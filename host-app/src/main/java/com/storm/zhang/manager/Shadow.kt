package com.storm.zhang.manager

import com.tencent.shadow.dynamic.host.DynamicPluginManager
import java.io.File

public class Shadow {

    companion object {

        public fun getPluginManager(apk: File): DynamicPluginManager? {
            var fixedPathPmUpdater = FixedPathPmUpdater(apk)
            var tempPm = fixedPathPmUpdater.latest

            if (tempPm != null) {
                return DynamicPluginManager(fixedPathPmUpdater)
            }
            return null
        }
    }
}