package com.storm.zhang.services

import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.tencent.shadow.dynamic.host.PluginProcessService
import com.tencent.shadow.sample.host.lib.LoadPluginCallback

public class PluginProcessPPS :  PluginProcessService{


    companion object{
        val TAG = "PluginProcessPPS"
    }

    constructor():super(){
        LoadPluginCallback.setCallback(object : LoadPluginCallback.Callback {
            override fun beforeLoadPlugin(partKey: String) {
                Log.d("PluginProcessPPS", "beforeLoadPlugin($partKey)")
            }

            override fun afterLoadPlugin(
                partKey: String,
                applicationInfo: ApplicationInfo,
                pluginClassLoader: ClassLoader,
                pluginResources: Resources,
            ) {
                Log.d(
                    "PluginProcessPPS",
                    "afterLoadPlugin(" + partKey + "," + applicationInfo.className + "{metaData=" + applicationInfo.metaData + "}" + "," + pluginClassLoader + ")"
                )
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.e(TAG,"PluginProcessPPS 已经创建,并且执行了 PluginProcessPPS的 onCreate()方法")
    }

}