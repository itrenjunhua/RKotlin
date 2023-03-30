package com.renj.kotlin.api

import com.renj.kotlin.bean.MainResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-29   11:26
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    @GET("article/list/{pageNo}/json")
    suspend fun getHomeList(@Path("pageNo") pageNo: Int): MainResponse
}