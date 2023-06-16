package com.storm.zhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.storm.zhang.PluginHelper
import com.storm.zhang.R
import com.storm.zhang.common.constant.Constant
import com.storm.zhang.databinding.ActivityPluginViewBinding
import com.storm.zhang.manager.pluginview.DynamicPluginViewManager

/**
 * - @author: ZhangTongXue
 * - @email:  @outlook.com
 * - @date: 2023/6/17 02:18
 * - @package_name: com.storm.zhang.activity
 * - @description:
 * - @usage:
 *
 */
class PluginViewActivity : AppCompatActivity() {

    companion object {
        val TAG = "PluginViewActivity"
    }

    private lateinit var mBinding: ActivityPluginViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPluginViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpListener()
    }

    private fun setUpListener() {

        mBinding.tvLoadPluginView.setOnClickListener {

            // ? 加载插件 view .

            PluginHelper.getInstance().singlePool.execute {

                var pluginViewManager = DynamicPluginViewManager(
                    this,
                    PluginHelper.getInstance().pluginViewFile!!
                )


                mBinding.llContainer.post {

                    var bundle = Bundle()
                    bundle.putString(Constant.KEY_PLUGIN_VIEW_TAGET, "one")

                    mBinding.llContainer.addView(
                        pluginViewManager.getView(bundle, null)
                    )
                }
            }
        }
    }
}