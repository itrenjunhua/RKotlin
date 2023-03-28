package com.renj.kotlin.http

import okhttp3.OkHttpClient
import okhttp3.Interceptor
import kotlin.Throws
import com.renj.kotlin.http.NetworkException
import com.renj.kotlin.http.OkHttpUtil
import com.renj.kotlin.http.RetrofitUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException
import kotlin.jvm.Volatile

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2018-04-02   11:27
 *
 *
 * 描述：用于在 网络连接异常时抛出
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
class NetworkException : IllegalStateException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}