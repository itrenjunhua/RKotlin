package com.renj.kotlin

import android.app.Application
import com.renj.kotlin.api.ApiService
import com.renj.kotlin.http.RetrofitUtil
import com.renj.kotlin.utils.AndroidUtils

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-29   11:27
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidUtils.init(this)
        RetrofitUtil.newInstance()
            .addApiServerClass(ApiService::class.java)
//            .addInterceptor { chain ->
//                val originalRequest: Request = chain.request()
//                // 拼接 APP_TOKEN 头
//                val sessionIdRequest = originalRequest.newBuilder()
//                    .addHeader(AppConfig.APP_TOKEN_KEY, AppConfig.APP_TOKEN_VALUE)
//                    .build()
//                chain.proceed(sessionIdRequest)
//            }
            .initRetrofit(ApiService.Companion.BASE_URL)
    }
}