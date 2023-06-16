package com.storm.zhang.manager

import com.tencent.shadow.dynamic.host.PluginManagerUpdater
import java.io.File
import java.util.concurrent.Future

public class FixedPathPmUpdater(private var apk:File) : PluginManagerUpdater {


    override fun wasUpdating(): Boolean {
       return false
    }

    override fun update(): Future<File>? {
       return null
    }

    override fun getLatest(): File {
        return apk
    }

    override fun isAvailable(file: File?): Future<Boolean>? {
       return null
    }
}