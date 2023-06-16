package com.storm.zhang.plugin.view.widget

import android.content.Context
import android.os.Bundle

import android.view.View
import android.widget.TextView

import com.blankj.utilcode.util.SizeUtils
import com.storm.zhang.common.constant.Constant
import com.storm.zhang.common.plugin.PluginViewEventInterface
import com.storm.zhang.common.plugin.PluginViewInterface

/**
 * - @author: ZhangTongXue
 * - @email:  @outlook.com
 * - @date: 2023/6/17 01:27
 * - @package_name: com.storm.zhang.plugin.view.widget
 * - @description:
 * - @usage:
 *
 *
 * 此处可以定义出一个 输出 view 的工具类
 */

public class CustomTextView : PluginViewInterface {

    lateinit var context: Context

    constructor(context: Context) {
        this.context = context
    }


    /**
     *
     * @param pluginViewEventInterface PluginViewEventInterface
     * @return View
     */
    override fun getView(pluginViewEventInterface: PluginViewEventInterface?): View {
        var textview = TextView(context)
        textview.text = "插件中返回的 view "
        textview.textSize = SizeUtils.sp2px(18f).toFloat()
        return textview
    }

    override fun getView(
        bundle: Bundle?,
        pluginViewEventInterface: PluginViewEventInterface?,
    ): View {
        if (null == bundle) {
            var textview = TextView(context)
            textview.text = "插件中默认的 view "
            textview.textSize = SizeUtils.sp2px(18f).toFloat()
            return textview
        }

        var target: String? = bundle.getString(Constant.KEY_PLUGIN_VIEW_TAGET, "")


        return when (target) {

            "normal" -> {
                var textview = TextView(context)
                textview.text = "默认显示的 view "
                textview.textSize = SizeUtils.sp2px(18f).toFloat()
                return textview
            }

            "one" -> {
                var textview = TextView(context)
                textview.text = "一月份显示 view "
                textview.textSize = SizeUtils.sp2px(18f).toFloat()
                return textview
            }

            else -> {
                throw RuntimeException("未找到对应的 view ")
            }
        }
    }
}