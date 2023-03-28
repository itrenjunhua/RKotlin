package com.renj.kotlin.http

import androidx.annotation.NonNull
import com.renj.kotlin.utils.ListUtils
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.HashMap
import kotlin.NullPointerException

/**
 * ======================================================================
 * 作者：Renj
 *
 *
 * 创建时间：2017-05-11   18:08
 *
 *
 * 描述：Retrofit工具类，封装Retrofit相关方法
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
class RetrofitUtil private constructor() {
    /**
     * 增加ApiServer
     *
     * @param apiServer
     * @return
     */
    fun addApiServerClass(apiServer: Class<*>): RetrofitUtil {
        apiServerList.add(apiServer)
        return RETROFIT_UTIL
    }

    /**
     * 增加拦截器
     *
     * @param interceptor
     * @return
     */
    fun addInterceptor(interceptor: Interceptor): RetrofitUtil {
        OkHttpUtil.interceptorList.add(interceptor)
        return RETROFIT_UTIL
    }

    /**
     * 增加网络拦截器
     *
     * @param interceptor
     * @return
     */
    fun addNetworkInterceptor(interceptor: Interceptor?): RetrofitUtil {
        OkHttpUtil.networkInterceptorList.add(interceptor)
        return RETROFIT_UTIL
    }

    /**
     * 初始化Retrofit，如果有多个 baseUrl，可以调用多次该方法，但是需要重新调用 [.addApiServerClass] 增加接口类(ApiServer)
     * ([.addApiServerClass] 方法在 [.initRetrofit] 后会被初始化，需要再次调用)。<br></br><br></br>
     * 注意：1. 接口类(ApiServer)不能重复添加。每个baseUrl都应该有对应的一个或多个接口类(ApiServer)；但是每一个接口类(ApiServer)只能对应一个 baseUrl。<br></br>
     * 2. [.addInterceptor] 和 [.addNetworkInterceptor] 两个方法添加的拦截器会共用
     * (不会随着 [.initRetrofit] 的调用而重新初始化)<br></br><br></br>
     *
     * <pre>
     * RetrofitUtil.newInstance()
     * .addApiServerClass(ApiServer1.class)
     * .addInterceptor((chain) -> {
     * Request originalRequest = chain.request();
     * // 拼接 APP_TOKEN 头
     * Request sessionIdRequest = originalRequest.newBuilder()
     * .addHeader(AppConfig.APP_TOKEN_KEY, AppConfig.APP_TOKEN_VALUE)
     * .build();
     * return chain.proceed(sessionIdRequest);
     * })
     * .initRetrofit(ApiServer.BASE_URL1);
     *
     * // 虽然没有调用 addInterceptor() 方法，但是会共用上面的拦截信息
     * RetrofitUtil.newInstance()
     * .addApiServerClass(ApiServer2.class)
     * .initRetrofit(ApiServer.BASE_URL2);
    </pre> *
     *
     * @return
     */
    fun initRetrofit(baseUrl: String) {
        if (!ListUtils.isEmpty(apiServerList)) {
            throw NullPointerException("请先调用 RetrofitUtil#addApiServer() 方法增加至少一个接口类(ApiServer)")
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl) // 增加返回值为String的支持
            .addConverterFactory(GsonConverterFactory.create()) // 增加返回值为Observable<T>的支持
            .client(OkHttpUtil.initOkHttp())
            .build()

        for (aClass in apiServerList) {
            if (!apiServiceMap.containsKey(aClass)) {
                throw IllegalStateException("一个接口类(ApiServer)不能被初始化多次：" + aClass.name)
            }
            apiServiceMap[aClass] = retrofit.create(aClass)
        }
        apiServerList.clear()
    }

    /**
     * 获取指定的 ApiServer，该 ApiServer 必须是通过 [.addApiServerClass] 方法初始化的，否则返回 `null`
     *
     * @param apiServerClass 必须是通过 [.addApiServerClass] 方法初始化的
     * @param <T>
     * @return
    </T> */
    fun <T> getApiService(apiServerClass: Class<T>): T? {
        return apiServiceMap[apiServerClass] as T?
    }

    companion object {
        private val RETROFIT_UTIL = RetrofitUtil()
        private val apiServerList: MutableList<Class<*>> = ArrayList()
        private val apiServiceMap: MutableMap<Class<*>, Any> = HashMap()
        fun newInstance(): RetrofitUtil {
            return RETROFIT_UTIL
        }
    }
}

