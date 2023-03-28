package com.renj.kotlin.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.IntDef
import com.renj.kotlin.utils.AndroidUtils

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 *
 * 创建时间：2018-04-02   11:30
 *
 *
 * 描述：网路工具类
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
object NetWorkUtils {
    /**
     * 判断是否连接到网络了
     *
     * @return true：已经连接到网络 false;未连接到网络
     */
    val isNetworkAvailable: Boolean
        get() {
            val cm: ConnectivityManager = AndroidUtils.getApplication()!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }

    /**
     * 获取网络类型
     *
     * @return [ConnectionType]
     */
    private val networkType: Int
        get() {
            val connMgr: ConnectivityManager = AndroidUtils.getApplication()!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
            var networkType = ConnectionType.NO_CONNECTION
            if (networkInfo != null) {
                val type: Int = networkInfo.type
                networkType =
                    if (type == ConnectivityManager.TYPE_WIFI) ConnectionType.WIFI else ConnectionType.MOBILE
            }
            return networkType
        }

    /**
     * 流量/手机数据 是否打开
     *
     * @return
     */
    val isMobileDataEnable: Boolean
        get() {
            val connectivityManager: ConnectivityManager =
                AndroidUtils.getApplication()?.getSystemService(
                    Context.CONNECTIVITY_SERVICE
                ) as ConnectivityManager
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                ?.isConnectedOrConnecting ?: false
        }

    /**
     * 是否连接WiFi
     *
     * @return
     */
    val isWiFi: Boolean
        get() = networkType == ConnectionType.WIFI

    /**
     * 网络类型
     */
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [ConnectionType.NO_CONNECTION, ConnectionType.MOBILE, ConnectionType.WIFI])
    annotation class ConnectionType {
        companion object {
            const val NO_CONNECTION = -1
            const val MOBILE = 0
            const val WIFI = 1
        }
    }
}