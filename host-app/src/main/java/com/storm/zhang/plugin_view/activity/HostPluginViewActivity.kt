package com.storm.zhang.plugin_view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process.myPid
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.storm.zhang.PluginHelper
import com.storm.zhang.R
import com.storm.zhang.common.constant.Constant
import com.storm.zhang.databinding.ActivityHostPluginViewBinding
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder

/**
 *  plugin 插件 view
 */
public class HostPluginViewActivity : AppCompatActivity(), HostAddPluginViewContainer {


    companion object {
        val TAG = "HostPluginViewActivity"


    }


    lateinit var mBinding: ActivityHostPluginViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHostPluginViewBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
//        PluginHelper.getInstance().init(this.applicationContext)


    }

    override fun onResume() {
        super.onResume()
        setUpListener()
    }

    private fun setUpListener() {

        mBinding.btnLoadPluginView.setOnClickListener {

            addLocalView()

            loadPluginView(it)
        }
    }

    // * 添加本地 view
    private fun addLocalView() {


        var view = LayoutInflater.from(this).inflate(R.layout.layout_local_view, null, false)

        mBinding.llPluginViewContainer.addView(view)


    }

    /**
     *  加载插件 view
     * @param view View?
     */
    private fun loadPluginView(view: View?) {
        view?.isEnabled = false

        var intent = Intent()

        with(intent) {
            this.setPackage(packageName)
            this.action = "storm_host.manager.startPluginService"

            var hashId = System.identityHashCode(this)

            HostAddPluginViewContainerHolder.instances[hashId] = this@HostPluginViewActivity

            this.putExtra("id", hashId)
            this.putExtra("layout_id", 1)

            // ! 这里的传递的值是为了作用在启动插件的的时候,先加载 view ,
            // ! 在原来的例子中, 需要先点击启动插件才能作用 , 所以这里传递了 parkey 和 pluginzipfile 来 保证加载 view
            // ! 的时候 先保证加载插件.
//
//
//            this.putExtra(
//                Constant.KEY_PLUGIN_ZIP_PATH,
//                PluginHelper.getInstance().pluginZipFile!!.absolutePath)
//
//            this.putExtra(Constant.KEY_PLUGIN_PART_KEY, "plugin1")
            // * 可以在这里添加 跳转的 hostaddpluginviewervice

            sendBroadcast(intent)
        }

    }

    override fun addView(view: View?) {
        LogUtils.e(TAG, "获取的 view 信息 --> ${view.toString()}")

        LogUtils.e(TAG,"host app 当前线程 --> ${myPid()}")
        mBinding.llPluginViewContainer.addView(view)


    }


}