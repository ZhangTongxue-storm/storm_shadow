package com.storm.zhang.manager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.LayoutInflater
import com.storm.zhang.common.constant.Constant
import com.tencent.shadow.dynamic.host.EnterCallback
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

public class StormPluginManager : FastPluginManager {

    companion object {
        val TAG = "StormPluginManager"
    }

    private lateinit var mCurrentContext: Context

    constructor(context: Context) : super(context) {
        this.mCurrentContext = context
    }

    private val executorService: ExecutorService by lazy {

        Executors.newSingleThreadExecutor()
    }

    override fun getName(): String {
        return "storm_manager" + mPartKey
    }


    override fun getPluginProcessServiceName(partKey: String?): String {
        return when (partKey) {

            Constant.PART_KEY_PLUGIN_MAIN_APP -> "com.storm.zhang.services.PluginProcessPPS"
            Constant.PART_KEY_PLUGIN_ANOTHER_APP -> "com.storm.zhang.services.PluginProcessPPS2"

            else -> {
                throw IllegalArgumentException("unexpected plugin load rquest : $partKey  执行 getPluginProcessServiceName 发生异常")
            }
        }
    }


    override fun enter(context: Context?, fromId: Long, bundle: Bundle?, callback: EnterCallback?) {

        when (fromId) {
            Constant.FROM_ID_NOOP -> {}
            Constant.FROM_ID_START_ACTIVITY -> {
                context?.let {
                    onStartActivity(it, bundle, callback)
                }
            }

            Constant.FROM_ID_CLOSE -> close()

            Constant.FROM_ID_LOAD_VIEW_TO_HOST -> {

                context?.let { loadViewToHost(it, bundle, callback) }
            }

            Constant.FROM_ID_PLUGIN_LOAD -> {

                context?.let {
                    preLoadPlugin(it, bundle, callback)
                }
            }

            else -> throw IllegalArgumentException("formId not found  此 formId == $fromId 找不到对应 ")
        }

    }

    /**
     * 加载 view
     * * 可以在 bundle 中设置跳转的 service
     * ! 这里需要考虑插件的加载, 如果没有预先加载插件的时候, 那么就有可能加载不出来.
     * ! 所以这里可以预先处理一下加载的插件.
     * @param context Context
     * @param bundle Bundle?
     */
    private fun loadViewToHost(context: Context, bundle: Bundle?, callback: EnterCallback?) {
        var pluginIntent = Intent()

        // *  这里可以根据 bundle 来确定跳转的 service .
        pluginIntent.setClassName(
            context.packageName,
            "com.storm.zhang.service.StormAddPluginViewService"
        )

        bundle?.let {
            pluginIntent.putExtras(it)
        }


        try {
            mPluginLoader.startPluginService(pluginIntent)
        } catch (e: RemoteException) {
            throw RuntimeException("loadViewToHost方法 发生异常 mPluginLoader.startPluginService 发生异常  --> $e")
        }

    }

    private var mPartKey: String = ""


    /**
     * * 预处理 通过 partKey 加载插件.
     * @param context Context
     * @param bundle Bundle?
     */
    private fun preLoadPlugin(context: Context, bundle: Bundle?, callback: EnterCallback?) {

        var pluginZipPath = bundle?.getString(Constant.KEY_PLUGIN_ZIP_PATH)
        var partKey = bundle?.getString(Constant.KEY_PLUGIN_PART_KEY)

        partKey?.let {
            this.mPartKey = it
        }


        executorService.execute {
            try {

//                pluginZipPath?.let {
//
//                    var installPlugin = installPlugin(it, null, true)
//
//                    loadPlugin(installPlugin.UUID,partKey)
//
//                }

                if (pluginZipPath != null && partKey != null) {

                    var installPlugin = installPlugin(pluginZipPath, null, true)
                    // ! 这里处理需要 有可能是多个 partKey 的插件包.

                    loadPlugin(installPlugin.UUID, partKey)
                    callApplicationOnCreate(partKey)
                }

            } catch (e: Exception) {
                throw RuntimeException("preLoadPlugin() 报错 异常信息-->  ${e} ")
            }
        }


    }

    /**
     * 启动 activity
     */
    private fun onStartActivity(context: Context, bundle: Bundle?, callback: EnterCallback?) {
        var pluginZipPath = bundle?.getString(Constant.KEY_PLUGIN_ZIP_PATH)
        var partKey = bundle?.getString(Constant.KEY_PLUGIN_PART_KEY)
        partKey?.let {
            this.mPartKey = partKey

        }
        var className = bundle?.getString(Constant.KEY_ACTIVITY_CLASSNAME)

        if (null == className) {
            throw NullPointerException("onStartActivity() 获取 className == null")
        }

        bundle?.let {
            Log.e(
                TAG, "pluginZipPath --> ${it.getString(Constant.KEY_PLUGIN_ZIP_PATH)} " +
                        "partKey --> ${it.getString(Constant.KEY_PLUGIN_PART_KEY)} " +
                        "className --> ${it.getString(Constant.KEY_ACTIVITY_CLASSNAME)}"
            )

        }

        val extras = bundle?.getBundle(Constant.KEY_EXTRAS)

        callback?.let {
            val view =
                LayoutInflater.from(mCurrentContext).inflate(R.layout.activity_load_plugin, null)
            it.onShowLoadingView(view)
        }

        executorService.execute {
            try {
                val installPlugin = installPlugin(pluginZipPath, null, true)


                Log.e(TAG, "从 intent 获取的 partKey --> $partKey")
//                  // 如果一个包里面有多个 apk  那么就需要加载其他的 apk 的 partkey 传递的这个应该是一个主 key
                // 然后如果要可以的话, 可以通过 extra 来传递一个包内其他的partkey


//                loadPlugin(installPlugin.UUID, Constant.PART_KEY_PLUGIN_MAIN_APP)
//                loadPlugin(installPlugin.UUID, Constant.PART_KEY_PLUGIN_ANOTHER_APP)
//                callApplicationOnCreate(Constant.PART_KEY_PLUGIN_MAIN_APP)
//                callApplicationOnCreate(Constant.PART_KEY_PLUGIN_ANOTHER_APP)
                // ! 这里需要考虑的是加载的 partKey 这里是打包插件的时候, 只打包了一个 plugin apk ,
                // ! 如果是多个的话, 那么需要 找出所有的 partKey 进行加载

                // !  这里说明了在插件包中的 某一个 partKey 一个插件包中可能有多个插件 那么一个插件包多个 apk
                // ! 中会有多个 partkey, 所以这里也可以 比如有依赖关系的加载可以一起加载
                //! 分几种情况
                //! 1. 一个插件包中只有一个插件, 那么直接对应 partKey
                //! 2. 一个插件包中有多个插件apk 考虑他们之间的对应关系了
                //!     1), 多个 apk plugin 之间没有任何联系, 那么就可以在调用执行具体的业务的时候加载
                //!      2). 就一次性想要都加载完成, 减少用户的时间等待. 那么就直接从 bundle 中 对应的 partkey 列表
                //!         多个一起加载
                //!      3). 有依赖关系, 那么先加载需要依赖的partKey, 然后加载当前的 partKey 对应的 apk 插件.
                // 然后
                loadPlugin(installPlugin.UUID, partKey)
                callApplicationOnCreate(partKey)

                var pluginIntent = Intent()

                pluginIntent.setClassName(context.packageName, className)

                extras?.let { pluginIntent.replaceExtras(extras) }

                Log.e(
                    TAG,
                    "--- 检测 ---  mPluginLoader ---> 是否为 null --> ${mPluginLoader == null}"
                )

                var intent = mPluginLoader?.convertActivityIntent(pluginIntent)
                intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                mPluginLoader?.startActivityInPluginProcess(intent)

            } catch (e: Exception) {
                throw RuntimeException("onStartActivity 方法 异常 ----> ")
            }

            callback?.onCloseLoadingView()
        }
    }


}