package com.storm.zhang.plugin_view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.storm.zhang.MyApplication
import com.storm.zhang.PluginHelper
import com.storm.zhang.common.constant.Constant

public class MainProcessManagerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {

        // ! 当然在这里, HostPluginActivity 把 pluginZipFile  和 partKey 添加到这里
        // ! bundle 中.


        MyApplication.getApp().getPluginManager(
            "plugin1",
            PluginHelper.getInstance().pluginManagerFile!!
        ).enter(
            context, Constant.FROM_ID_LOAD_VIEW_TO_HOST,
            intent.extras, null
        )

    }


}