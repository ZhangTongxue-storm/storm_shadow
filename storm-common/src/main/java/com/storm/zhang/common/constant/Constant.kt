package com.storm.zhang.common.constant


public open class Constant {


    companion object {

        /**
         * 插件安装地址
         */

        const val KEY_PLUGIN_ZIP_PATH: String = "pluginZipPath"

        /**
         * 打开的 view
         */

        const val KEY_PLUGIN_VIEW_PATH: String = "KEY_PLUGIN_VIEW_PATH"

        /**
         * 打开 Activity 名称
         */

        const val KEY_ACTIVITY_CLASSNAME: String = "KEY_ACTIVITY_CLASSNAME"

        /**
         *  service (相关 单独打开 view )
         */
        const val KEY_SERVICE_CLASSNAME : String = "KEY_SERVICE_CLASSNAME"

        /**
         * 额外字段
         */
        const val KEY_EXTRAS: String = "KEY_EXTRAS"

        /**
         * 插件名称
         */

        const val KEY_PLUGIN_PART_KEY: String = "KEY_PLUGIN_PART_KEY"

        /**
         * UUID
         */
        const val KEY_PLUGIN_UUID: String = "KEY_PLUGIN_UUID"

        /**
         * 获取插件中 view 的类型.
         */
        const val KEY_PLUGIN_VIEW_TAGET : String = "KEY_PLUGIN_VIEW_TARGET"

        /**
         * UUID NICKNAME
         */
        const val KEY_PLUGIN_UUID_NICKNAME: String = "KEY_PLUGIN_UUID_NICKNAME"

        const val PART_KEY_PLUGIN_MAIN_APP: String = "plugin1"
        const val PART_KEY_PLUGIN_ANOTHER_APP: String = "plugin2"

        const val FROM_ID_NOOP: Long = 1000
        const val FROM_ID_START_ACTIVITY: Long = 1002
        const val FROM_ID_CLOSE: Long = 1003

        /**
         * 获取 view
         */
        const val FROM_ID_LOAD_VIEW_TO_HOST: Long = 1004

        const val FROM_ID_PLUGIN_LOAD: Long = 1005
    }


}