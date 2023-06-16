package com.storm.zhang

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process.myPid
import android.os.StrictMode
import android.webkit.WebView
import com.storm.zhang.common.InitApplication
import com.storm.zhang.manager.Shadow
import com.tencent.shadow.core.common.LoggerFactory
import com.tencent.shadow.dynamic.host.DynamicRuntime
import com.tencent.shadow.dynamic.host.PluginManager
import com.tencent.shadow.sample.host.lib.HostUiLayerProvider
import java.io.File

public class MyApplication : InitApplication() {

    companion object {
        lateinit var appContext: MyApplication


        /**
         * 获取 appcontext
         * @return MyApplication
         */
        public fun getApp(): MyApplication {
            return appContext
        }


        private fun detectNonSdkApiUsageOnAndroidP() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                return
            }

            var builder: StrictMode.VmPolicy.Builder =
                StrictMode.VmPolicy.Builder();
            builder.detectNonSdkApiUsage()
            StrictMode.setVmPolicy(builder.build())
        }

        private fun setWebViewDataDirectorySuffix() {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                return
            }

            WebView.setDataDirectorySuffix(Application.getProcessName())
        }

        private fun isProcess(context: Context, processName: String): Boolean {

            var currentProcName = ""

            var manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            for (processInfo in manager.runningAppProcesses) {
                if (processInfo.pid == myPid()) {
                    currentProcName = processInfo.processName
                    break
                }
            }
            return currentProcName.endsWith(processName)
        }
    }

    private var mSingletonPluginManager: PluginManager? = null


    var managerMap: HashMap<String, PluginManager> = hashMapOf()

    override fun onCreate() {
        super.onCreate()
        appContext = this

        detectNonSdkApiUsageOnAndroidP()
        setWebViewDataDirectorySuffix()
        LoggerFactory.setILoggerFactory(AndroidLogLoggerFactory())
        initProgress()
        if (MyApplication.isProcess(this, packageName)) {
            PluginHelper.getInstance().init(this)
        }
        HostUiLayerProvider.init(this)
    }


    private fun initProgress() {

        if (isProcess(this, ":plugin")) {
            //在全动态架构中，Activity组件没有打包在宿主而是位于被动态加载的runtime，
            //为了防止插件crash后，系统自动恢复crash前的Activity组件，此时由于没有加载runtime而发生classNotFound异常，导致二次crash
            //因此这里恢复加载上一次的runtime
            DynamicRuntime.recoveryRuntime(this)
        }
    }


    public fun loadPluginManager(apkFile: File) {
        if (mSingletonPluginManager == null) {
            mSingletonPluginManager = Shadow.getPluginManager(apkFile)
        }
    }

    public fun getSinglePluginManager(): PluginManager? {
        return mSingletonPluginManager
    }


    public fun getPluginManager(key: String, apkFile: File): PluginManager {
        var pluginManager = managerMap[key]

        if (pluginManager == null) {
            pluginManager = Shadow.getPluginManager(apkFile)
            managerMap[key] = pluginManager!!
            return pluginManager
        }else{
            return pluginManager
        }
    }


}