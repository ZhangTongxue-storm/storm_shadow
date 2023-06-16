package com.storm.zhang.storm_loader.loader

import android.content.ComponentName
import android.content.Context
import com.tencent.shadow.core.loader.infos.ContainerProviderInfo
import com.tencent.shadow.core.loader.managers.ComponentManager

public class StormComponentManager : ComponentManager {

    companion object {
        val DEFAULT_ACTIVITY = "com.storm.zhang.runtime.PluginDefaultProxyActivity"
        val SINGLE_INSTANCE_ACTIVITY = "com.storm.zhang.runtime.PluginSingleInstance1ProxyActivity"
        val SINGLE_TASK_ACTIVITY = "com.storm.zhang.runtime.PluginSingleTask1ProxyActivity"
    }

    private lateinit var context: Context

    constructor(context: Context) {
        this.context = context
    }

    /**
     * 配置插件 activity 到壳子 activity 的对应关系
     * @param pluginActivity ComponentName 插件 activity
     * @return ComponentName 壳子 activity
     */
    override fun onBindContainerActivity(pluginActivity: ComponentName): ComponentName {
        //参数是全路径 主要对应非默认开启模式的 activity , singletask 需要单独对应的 activity
        // pluginActivity.getClassName() == com.storm.zhang.plugin.MainActivity
        when (pluginActivity.className) {
            // 这里配置对应的对应关系
        }
        return ComponentName(context, DEFAULT_ACTIVITY)
    }

    /**
     * 配置对应宿主中预注册的壳子 contentProvider 信息
     * @param pluginContentProvider ComponentName
     * @return ContainerProviderInfo
     */
    override fun onBindContainerContentProvider(pluginContentProvider: ComponentName): ContainerProviderInfo {
        return ContainerProviderInfo(
            "com.tencent.shadow.core.runtime.container.PluginContainerContentProvider",
            context.packageName + ".contentprovider.authority.dynamic"
        )
    }


}