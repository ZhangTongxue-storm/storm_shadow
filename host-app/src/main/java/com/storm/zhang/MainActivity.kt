package com.storm.zhang

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.storm.zhang.activity.PluginViewActivity
import com.storm.zhang.common.constant.Constant

import com.storm.zhang.databinding.ActivityMainBinding
import com.storm.zhang.plugin_view.activity.HostPluginViewActivity
import com.tencent.shadow.dynamic.host.EnterCallback

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "MainActivity"
    }

    private lateinit var mBinding: ActivityMainBinding

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpListener()
    }

    private fun setUpListener() {

        mBinding.tvTaskTwo.setOnClickListener {
            PluginHelper.getInstance().singlePool.execute {
                MyApplication.getApp().getPluginManager(
                    "plugin2", PluginHelper.getInstance().pluginManagerFile!!
                )

                var bundle = Bundle()

                bundle.putString(
                    Constant.KEY_PLUGIN_ZIP_PATH,
                    PluginHelper.getInstance().pluginZipFile2!!.absolutePath
                )

                bundle.putString(
                    Constant.KEY_PLUGIN_PART_KEY, "plugin2"
                )

                bundle.putString(
                    Constant.KEY_ACTIVITY_CLASSNAME, "com.storm.zhang.activity.MainActivity"
                )



                MyApplication.getApp()
                    .getPluginManager(
                        "plugin2", PluginHelper.getInstance().pluginManagerFile!!
                    ).enter(this@MainActivity,
                        Constant.FROM_ID_START_ACTIVITY, bundle,
                        object : EnterCallback {
                            override fun onShowLoadingView(view: View?) {


                            }

                            override fun onCloseLoadingView() {

                            }

                            override fun onEnterComplete() {

                            }

                        })

            }
        }




        mBinding.tvTaskOne.setOnClickListener {

            PluginHelper.getInstance().singlePool.execute {

                MyApplication.getApp()
                    .getPluginManager("plugin1", PluginHelper.getInstance().pluginManagerFile!!)


                var bundle = Bundle()

                bundle.putString(
                    Constant.KEY_PLUGIN_ZIP_PATH,
                    PluginHelper.getInstance().pluginZipFile!!.absolutePath
                )
                bundle.putString(Constant.KEY_PLUGIN_PART_KEY, "plugin1")
                bundle.putString(
                    Constant.KEY_ACTIVITY_CLASSNAME,
                    "com.storm.zhang.activity.SplashActivity"
                )

                MyApplication.getApp().getPluginManager(
                    "plugin1",
                    PluginHelper.getInstance().pluginManagerFile!!
                )
                    .enter(this@MainActivity, Constant.FROM_ID_START_ACTIVITY,
                        bundle, object : EnterCallback {
                            override fun onShowLoadingView(view: View?) {

                                mHandler.post {

                                    mBinding.llLoad.addView(view)
                                    mBinding.llLoad.isVisible = true
                                }


                            }

                            override fun onCloseLoadingView() {

                                mHandler.post {
                                    mBinding.llLoad.removeAllViews()
                                    mBinding.llLoad.isVisible = false

                                }

                            }

                            override fun onEnterComplete() {

                            }

                        })

            }

        }

        /**
         * 添加 插件中的 view
         */

        mBinding.tvAddView.setOnClickListener {


            onLoadPluginPre()
            startActivity(Intent(this, HostPluginViewActivity::class.java))
        }

        mBinding.tvAddPluginView.setOnClickListener {
            startActivity(Intent(this, PluginViewActivity::class.java))
        }


    }

    private fun onLoadPluginPre() {

        PluginHelper.getInstance().singlePool.execute {
            MyApplication.getApp()
                .getPluginManager("plugin1", PluginHelper.getInstance().pluginManagerFile!!)


            var bundle = Bundle()

            bundle.putString(
                Constant.KEY_PLUGIN_ZIP_PATH,
                PluginHelper.getInstance().pluginZipFile!!.absolutePath
            )
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, "plugin1")


            MyApplication.getApp().getPluginManager(
                "plugin1",
                PluginHelper.getInstance().pluginManagerFile!!
            ).enter(this, Constant.FROM_ID_PLUGIN_LOAD, bundle,
                object : EnterCallback {
                    override fun onShowLoadingView(view: View?) {


                    }

                    override fun onCloseLoadingView() {
//
                    }

                    override fun onEnterComplete() {
//
                    }

                })
        }
    }
}

