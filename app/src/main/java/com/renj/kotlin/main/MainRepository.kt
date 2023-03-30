package com.renj.kotlin.main

import com.renj.kotlin.base.BaseRepository
import com.renj.kotlin.bean.MainResponse

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-29   15:42
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class MainRepository : BaseRepository() {
    suspend fun getMainPageData(pageNo: Int): MainResponse {
        return requestHttp {
            return@requestHttp apiService.getHomeList(pageNo)
        }
    }
}