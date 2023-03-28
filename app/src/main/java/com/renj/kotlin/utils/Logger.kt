package com.renj.kotlin.utils

import android.util.Log
import androidx.annotation.NonNull

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 *
 *
 * 创建时间：2018-11-13   10:09
 *
 *
 * 描述：自定义日志打印工具类，支持设置Tag，打印消息的级别和是否打印全类名(类的全路径名)<br></br>
 * 打印形式为：(全)类名.方法名(所在行数): 打印的信息
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
object Logger {
    /**
     * Log日志的 Tag，默认 Logger
     */
    private var TAG = "Logger"

    /**
     * 是否打印全部类名(类的全路径名)，默认false
     */
    private var IS_FULL_CLASSNAME = false

    /**
     * 是否 debug 版本，true 是调试版本；false 是正式版本
     */
    private val isDebug = AndroidUtils.isDebug

    /**
     * 设置是否打印类的全路径名
     *
     * @param isFullClassName true：打印类的全路径名
     */
    fun isFullClassName(isFullClassName: Boolean) {
        IS_FULL_CLASSNAME = isFullClassName
    }

    /**
     * 设置Log的Tag，默认 "Logger"
     *
     * @param tag
     */
    fun setAppTAG(tag: String) {
        TAG = tag
    }

    fun v(msg: String) {
        if (isDebug) {
            Log.v(TAG, logTitle + msg)
        }
    }

    fun d(msg: String) {
        if (isDebug) {
            Log.d(TAG, logTitle + msg)
        }
    }

    fun i(msg: String) {
        if (isDebug) {
            Log.i(TAG, logTitle + msg)
        }
    }

    fun w(msg: String) {
        if (isDebug) {
            Log.w(TAG, logTitle + msg)
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, logTitle + msg)
        }
    }

    /**
     * 返回类名(根据是否设置了打印全类名返回响应的值)，方法名和日子打印所在行数
     *
     * @return (全)类名.方法名(所在行数):
     */
    @get:NonNull
    private val logTitle: String
        get() {
            val elm = Thread.currentThread().stackTrace[4]
            var className = elm.className
            if (!IS_FULL_CLASSNAME) {
                val dot = className.lastIndexOf('.')
                if (dot != -1) {
                    className = className.substring(dot + 1)
                }
            }
            return className + "." + elm.methodName + "(" + elm.lineNumber + ")" + ": "
        }
}