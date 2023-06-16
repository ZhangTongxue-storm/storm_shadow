package com.storm.zhang.common.plugin

import android.os.Bundle
import android.view.View

/**
 * 提供插件类的实现接口
 */
public interface PluginViewInterface {

    /**
     *  返回实现的 view
     */
    public fun getView(pluginViewEventInterface: PluginViewEventInterface?): View

    public fun getView(bundle: Bundle?, pluginViewEventInterface: PluginViewEventInterface?): View
}