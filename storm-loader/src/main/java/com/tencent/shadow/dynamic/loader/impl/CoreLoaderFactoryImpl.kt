package com.tencent.shadow.dynamic.loader.impl

import android.content.Context
import com.storm.zhang.storm_loader.loader.StormPluginLoader
import com.tencent.shadow.core.loader.ShadowPluginLoader

/**
 * 这个类的包名类名是固定的。
 * <p>
 * 见com.tencent.shadow.dynamic.loader.impl.DynamicPluginLoader#CORE_LOADER_FACTORY_IMPL_NAME
 */
public class CoreLoaderFactoryImpl : CoreLoaderFactory {
    override fun build(hostAppContext: Context): ShadowPluginLoader {

        return StormPluginLoader(hostAppContext)
    }


}