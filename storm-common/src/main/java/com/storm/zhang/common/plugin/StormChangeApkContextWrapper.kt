package com.storm.zhang.common.plugin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import android.content.res.AssetManager
import android.content.res.Resources
import android.view.LayoutInflater
import com.blankj.utilcode.util.LogUtils

public open class StormChangeApkContextWrapper : ContextWrapper {

    private lateinit var mResources: Resources

    private lateinit var mClassLoader: ClassLoader


    protected var mHostViewApplication: Application? = null
    protected var mHostViewActivity: Activity? = null

    private var mLayoutInflater: LayoutInflater? = null

    /**
     *
     * @param base Context
     * @param apkPath String
     * @param classloader ClassLoader
     * @constructor
     */
    constructor(base: Context, apkPath: String, classloader: ClassLoader) : super(base) {
        this.mClassLoader = classloader
        mResources = createResources(apkPath, base)


    }

    private fun createResources(apkPath: String, base: Context): Resources {

        val packageManager = base.packageManager
        val packageArchiveInfo = packageManager.getPackageArchiveInfo(apkPath, GET_META_DATA)

        packageArchiveInfo?.applicationInfo?.publicSourceDir = apkPath
        packageArchiveInfo?.applicationInfo?.sourceDir = apkPath

        try {
            return packageManager.getResourcesForApplication(packageArchiveInfo!!.applicationInfo)

        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
    }

    public fun getHostViewApplication(): Application? {
        return mHostViewApplication
    }


    public fun setHostViewApplication(hostViewApplication: Application) {
        this.mHostViewApplication = hostViewApplication
    }

    public fun getHostViewActivity(): Activity? {
        return mHostViewActivity
    }

    public fun setHostViewActivity(hostViewActivity: Activity) {
        this.mHostViewActivity = hostViewActivity
    }

    override fun getApplicationContext(): Context {
        return this
    }

    override fun getAssets(): AssetManager {
        return mResources.assets
    }

    override fun getResources(): Resources {
        return mResources
    }

    override fun getTheme(): Resources.Theme {
        LogUtils.e("getTheme", "获取设置的属性")
        return mResources.newTheme()
    }

    override fun getSystemService(name: String): Any {

        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mLayoutInflater == null) {
                var layoutInflater = super.getSystemService(name) as LayoutInflater
                mLayoutInflater = layoutInflater.cloneInContext(this)
            }
            return mLayoutInflater!!
        }

        return super.getSystemService(name)
    }

    override fun getClassLoader(): ClassLoader {
        return mClassLoader
    }

}