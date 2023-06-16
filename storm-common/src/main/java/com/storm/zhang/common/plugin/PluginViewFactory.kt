package com.storm.zhang.common.plugin

import android.content.Context

/**
 * 宿主只能通过工厂模式获取 apk
 * 返回子类 pluginViewInterface 的 实现接口
 */
public interface PluginViewFactory {
    fun build(context: Context): PluginViewInterface
}