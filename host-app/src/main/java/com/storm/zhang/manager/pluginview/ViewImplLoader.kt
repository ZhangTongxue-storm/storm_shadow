package com.storm.zhang.manager.pluginview

import android.app.Application
import android.content.Context
import com.storm.zhang.common.plugin.PluginViewFactory
import com.storm.zhang.common.plugin.PluginViewInterface
import com.storm.zhang.common.plugin.ProxyApplication
import com.tencent.shadow.core.common.InstalledApk
import com.tencent.shadow.dynamic.apk.ApkClassLoader
import com.tencent.shadow.dynamic.apk.ImplLoader
import java.io.File
import java.lang.Exception

public class ViewImplLoader : ImplLoader {


    companion object {
        val TAG = "ViewImplLoader"

        val FACTORY_CLASS_NAME = "com.storm.zhang.plugin.view.dynamic.impl.PluginViewFactoryImpl"

        val REMOTE_PLUGIN_MANAGER_INTERFACES = arrayOf<String>(
            "com.tencent.shadow.core.common",
            "com.tencent.shadow.dynamic.host",
        )
    }

    private lateinit var applicationContext: Context
    private lateinit var mContext: Context
    private lateinit var installedApk: InstalledApk

    constructor(context: Context, apk: File) {
        applicationContext = context.applicationContext
        this.mContext = context
        var root = File(applicationContext.filesDir, "HelloImplLoader")
        var odexDir = File(root, apk.lastModified().toString(Character.MAX_RADIX))

        odexDir.mkdirs()
        installedApk = InstalledApk(apk.absolutePath, odexDir.absolutePath, null)


    }

    override fun getCustomWhiteList(): Array<String> {
        return REMOTE_PLUGIN_MANAGER_INTERFACES
    }

    public fun load(): PluginViewInterface {

        // * 这里面 com.storm.zhang.plugin.view.dynamic.impl.WhiteList 就是在 插件 apk 中 的 WhiteList 的
        // * 位置.
        var apkClassLoader = ApkClassLoader(
            installedApk,
            javaClass.classLoader,
            loadWhiteList(installedApk, "com.storm.zhang.plugin.view.dynamic.impl.WhiteList", "sWhiteList"), 1
        )

        var contextForApi = ProxyApplication(
            applicationContext as Application,
            mContext, installedApk.apkFilePath, apkClassLoader
        )

        try {
            var factory =
                apkClassLoader.getInterface(PluginViewFactory::class.java, FACTORY_CLASS_NAME)

            return factory.build(contextForApi)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }




}