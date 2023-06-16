package com.storm.zhang.common.plugin

import android.app.Application
import android.content.ComponentCallbacks
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi

public class ProxyApplication : StormChangeApkContextWrapper, ComponentCallbacks2 {

    companion object {

        val TAG = "ProxyApplication"


        @RequiresApi(api = Build.VERSION_CODES.P)
        public fun getProcessName(): String? {
            return Application.getProcessName()
        }
    }

    lateinit var application: Application

    constructor(
        application: Application,
        base: Context,
        apkPath: String,
        classLoader: ClassLoader,
    ) : super(base, apkPath, classLoader) {

        this.application = application
        setHostViewApplication(this.application)

    }

    public fun onCreate() {
        application.onCreate()
    }

    override fun getApplicationContext(): Context {
        return this
    }

    public fun onTerminate() {
        application.onTerminate()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        application.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        application.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        application.onTrimMemory(level)
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks?) {

        application.registerComponentCallbacks(callback)
    }

    override fun unregisterComponentCallbacks(callback: ComponentCallbacks?) {
        application.unregisterComponentCallbacks(callback)
    }

    public fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callback)
    }

    public fun unregisterActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.unregisterActivityLifecycleCallbacks(callback)
    }


    public fun registerOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener) {
        application.registerOnProvideAssistDataListener(callback)
    }

    public fun unregisterOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener) {
        application.unregisterOnProvideAssistDataListener(callback)
    }


}