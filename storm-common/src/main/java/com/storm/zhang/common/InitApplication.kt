package com.storm.zhang.common

import android.app.Application
import com.blankj.utilcode.BuildConfig
import com.blankj.utilcode.util.LogUtils

public abstract class InitApplication : Application() {

    companion object {

        var loggerConfig: LogUtils.Config? = null

    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }


    /**
     * 初始化 logger
     */
    private fun initLogger() {

        loggerConfig = LogUtils.getConfig()
            .setLogSwitch(true)
            .setConsoleSwitch(BuildConfig.DEBUG)
            .setGlobalTag("storm_log")
            .setLog2FileSwitch(false)
            .setSingleTagSwitch(true)
            .setLogHeadSwitch(true)
            .setBorderSwitch(true)
            .setConsoleFilter(LogUtils.V)
            .setFileFilter(LogUtils.V)

    }
}