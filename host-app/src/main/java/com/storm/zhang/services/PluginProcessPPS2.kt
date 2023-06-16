package com.storm.zhang.services

import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.tencent.shadow.dynamic.host.PluginProcessService
import com.tencent.shadow.sample.host.lib.LoadPluginCallback

public class PluginProcessPPS2 : PluginProcessService {


    companion object {
        val TAG = "PluginProcessPPS2"
    }


    constructor():super(){
        LoadPluginCallback.setCallback(object : LoadPluginCallback.Callback {
            override fun beforeLoadPlugin(partKey: String) {
                Log.d("PluginProcessPPS2", "beforeLoadPlugin($partKey)")
            }

            override fun afterLoadPlugin(
                partKey: String,
                applicationInfo: ApplicationInfo,
                pluginClassLoader: ClassLoader,
                pluginResources: Resources,
            ) {
                Log.d(
                    "PluginProcessPPS2",
                    "afterLoadPlugin(" + partKey + "," + applicationInfo.className + "{metaData=" + applicationInfo.metaData + "}" + "," + pluginClassLoader + ")"
                )
            }
        })

    }
    override fun onCreate() {
        super.onCreate()
        LogUtils.e(TAG, " PluginProcessPPS2 创建 -->  PluginProcessPPS2 的 onCreate() 执行")
    }


}