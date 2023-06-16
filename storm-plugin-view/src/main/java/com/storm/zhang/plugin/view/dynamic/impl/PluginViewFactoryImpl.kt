package com.storm.zhang.plugin.view.dynamic.impl

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.storm.zhang.common.plugin.PluginViewFactory
import com.storm.zhang.common.plugin.PluginViewInterface
import com.storm.zhang.plugin.view.widget.CustomTextView


public final class PluginViewFactoryImpl : PluginViewFactory {


    companion object {
        val TAG = "PluginViewFactoryImpl"
    }

    override fun build(context: Context): PluginViewInterface {


        return CustomTextView(context)

    }
}