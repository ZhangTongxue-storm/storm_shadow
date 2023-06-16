package com.storm.zhang.service

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Process.myPid
import android.view.LayoutInflater
import com.blankj.utilcode.util.LogUtils
import com.storm.zhang.R
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder

/**
 * * 这个是通过 JobIntentService 实现的. 但是已经废弃了,
 * * 只能使用 windowManager 但是许多的 系统并不支持.
 */
//public class StormAddPluginViewService : JobIntentService() {
//
//    companion object{
//        val TAG =  "StormAddPluginViewService"
//    }
//
//
//
//
//    override fun onHandleWork(intent: Intent) {
//        TODO("Not yet implemented")
//    }
//
//
//}


/**
 * * 通过 IntentService 来实现.
 * @property uiHandle Handler
 */
public class StormAddPluginViewService : IntentService {


    constructor() : super("StormAddPluginViewService")


    companion object {
        val TAG = "StormAddPluginViewService"
        const val EXTRA_KEY_ID = "id"
        const val EXTRA_KEY_LAYOUT_ID = "layout_id"
    }


    private val uiHandle by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onHandleIntent(intent: Intent?) {
        var id = intent?.getIntExtra(EXTRA_KEY_ID, 0)
        var layoutId = intent?.getIntExtra(EXTRA_KEY_LAYOUT_ID, -1)

        LogUtils.e(
            TAG, "获取的 id --> $id" +
                    "获取的 layoutId --> $layoutId"
        )


        id?.let {
            LogUtils.e(TAG,"host app 当前线程 --> ${myPid()}")
            var viewContainer: HostAddPluginViewContainer?  =
                HostAddPluginViewContainerHolder.instances[it] as HostAddPluginViewContainer



            LogUtils.e(TAG, "获取的 viewContrainer 是否为 null ---> ${viewContainer == null}")

            uiHandle.post {
                var view = LayoutInflater.from(this)
                    .inflate(getPluginView(layoutId), null, false)

                viewContainer?.addView(view)
            }
        }


    }

    private fun getPluginView(layoutId: Int?): Int {

        return when (layoutId) {

            1 -> R.layout.layout_host_add_plugin_view

            else -> R.layout.layout_host_add_plugin_normal_view
        }

    }


}