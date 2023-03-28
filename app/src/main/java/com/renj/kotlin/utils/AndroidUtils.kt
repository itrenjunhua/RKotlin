package com.renj.kotlin.utils

import android.app.Application
import com.renj.kotlin.utils.AndroidUtils
import android.content.pm.ApplicationInfo
import java.lang.NullPointerException

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 *
 * 创建时间：2018-11-13   10:28
 *
 *
 * 描述：
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
object AndroidUtils {
    private var application: Application? = null
    fun init(application: Application?) {
        AndroidUtils.application = application
    }

    fun getApplication(): Application? {
        if (application == null) throw NullPointerException("请先调用 com.renj.common.AndroidUtils.init(Application application) 方法进行初始化")
        return application
    }

    val isDebug: Boolean
        get() {
            if (application == null) throw NullPointerException("请先调用 com.renj.common.AndroidUtils.init(Application application) 方法进行初始化")
            return application!!.applicationInfo != null &&
                    application!!.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        }
}