package com.storm.zhang

import android.content.Context
import com.blankj.utilcode.util.LogUtils


import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors

public class PluginHelper {

    companion object {

        val TAG = "PluginHelper"

        // 动态加载插件的管理 apk
        val sPluginManagerName: String = "storm-manager-debug.apk"
        val sPluginZip: String = "plugin-debug-1.zip"
        val sPluginZipZ = "plugin-debug-2.zip"


        const val sPluginview = "storm-plugin-view-debug.apk" //加载 view

        val sInstance: PluginHelper = PluginHelper()

        public fun getInstance(): PluginHelper {
            return sInstance
        }


    }

    var pluginManagerFile: File? = null

    var pluginViewFile: File? = null

    var pluginZipFile: File? = null

    var pluginZipFile2: File? = null

    public var singlePool = Executors.newSingleThreadExecutor()

    lateinit var mContext: Context
    public fun init(context: Context) {
        pluginManagerFile = File(context.filesDir, sPluginManagerName)

        pluginViewFile = File(context.filesDir, sPluginview)

        pluginZipFile = File(context.filesDir, sPluginZip)
        pluginZipFile2 = File(context.filesDir, sPluginZipZ)

        mContext = context.applicationContext


        singlePool.execute {
            preparePlugin()
        }
    }

    private fun preparePlugin() {

        try {

            var ios = mContext.assets.open(sPluginManagerName)
            FileUtils.copyInputStreamToFile(ios, pluginManagerFile)

            // 加载插件 view 的作用.
            val ios2 = mContext.assets.open(sPluginview)
            FileUtils.copyInputStreamToFile(ios2, pluginViewFile)

            var zip = mContext.assets.open(sPluginZip)
            FileUtils.copyInputStreamToFile(zip, pluginZipFile)

            val zip2 = mContext.assets.open(sPluginZipZ)
            FileUtils.copyInputStreamToFile(zip2, pluginZipFile2)

            LogUtils.d(TAG, "模拟所有插件下载完成, 下载完成,下载完成")

        } catch (e: IOException) {
            LogUtils.e(TAG, "preparePlugin ---> ${e.message}")
            throw RuntimeException("从 assets 中复制 apk 出错, ", e)
        }
    }


}