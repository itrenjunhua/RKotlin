package com.renj.kotlin.http

import com.renj.kotlin.utils.AndroidUtils
import com.renj.kotlin.utils.Logger.d
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * ======================================================================
 * 作者：Renj
 *
 *
 * 创建时间：2017-05-11   18:11
 *
 *
 * 描述：对OKHttp进行初始化的类(自定义配置OKHttp)
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
object OkHttpUtil {
    private lateinit var mOkHttpClient: OkHttpClient
    val interceptorList: MutableList<Interceptor?> = ArrayList<Interceptor?>()
    val networkInterceptorList: MutableList<Interceptor?> = ArrayList<Interceptor?>()

    /**
     * 初始化OkHttpClient，可以自定义配置
     */
    fun initOkHttp(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        // 增加网络状态监听拦截器
        builder.addInterceptor { chain ->
            if (NetWorkUtils.isNetworkAvailable) {
                chain.proceed(chain.request())
            } else {
                throw NetworkException("网络连接异常!!!")
            }
        }


        // 增加拦截器 addInterceptor
        if (interceptorList.size > 0) {
            for (interceptor in interceptorList) {
                if (interceptor != null) {
                    builder.addInterceptor(interceptor)
                }
            }
        }

        // 增加网络拦截器 addNetworkInterceptor
        if (networkInterceptorList.size > 0) {
            for (interceptor in networkInterceptorList) {
                if (interceptor != null) {
                    builder.addNetworkInterceptor(interceptor)
                }
            }
        }

        // Debug 模式下打印访问网络的地址和提交的参数
        if (AndroidUtils.isDebug) {
            builder.addNetworkInterceptor(HttpLoggingInterceptor { message -> d(message) }
                .setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        mOkHttpClient = builder.build()
        return mOkHttpClient
    }

    /**
     * 获取OkHttpClient
     *
     * @return
     */
    val okHttpClient: OkHttpClient
        get() = mOkHttpClient
}