package com.storm.zhang.plugin.view.dynamic.impl;


/**
 * * 这里是添加插件的 view 的白名单,
 * * 然后这里的包名是固定的, 对应了 在宿主 app 中对应的
 * * ApkClassLoader apkClassLoader = new ApkClassLoader(
 * *                 installedApk,
 * *                 getClass().getClassLoader(),
 * *                 loadWhiteList(installedApk,"com.xa.dynamic.impl.WhiteList","sWhiteList"),
 * *                 1
 * *         );
 * 加载宿主的白名单对应的类
 */

public interface WhiteList {
    String[] sWhiteList = new String[]{

            "com.storm.zhang.common.plugin",
            "com.blankj.utilcode.util"
    };

}
