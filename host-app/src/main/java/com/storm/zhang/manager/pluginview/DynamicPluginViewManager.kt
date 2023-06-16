package com.storm.zhang.manager.pluginview

import android.content.Context
import android.os.Bundle
import android.view.View
import com.storm.zhang.common.plugin.PluginViewEventInterface
import com.storm.zhang.common.plugin.PluginViewInterface
import java.io.File

public class DynamicPluginViewManager : PluginViewInterface {


    lateinit var viewImplLoader: ViewImplLoader
    lateinit var pluginViewInterface: PluginViewInterface

    constructor(context: Context, latestImplApk: File) {
        viewImplLoader = ViewImplLoader(context, latestImplApk)
        pluginViewInterface = viewImplLoader.load()
    }

    override fun getView(pluginViewEventInterface: PluginViewEventInterface?): View {

        return pluginViewInterface.getView( pluginViewEventInterface)
    }

    override fun getView(
        bundle: Bundle?,
        pluginViewEventInterface: PluginViewEventInterface?,
    ): View {
        return pluginViewInterface.getView(bundle, pluginViewEventInterface)
    }


}