package com.renj.kotlin.main

import com.renj.kotlin.base.BaseRepository
import com.renj.kotlin.base.BaseResponse
import kotlinx.coroutines.delay

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
    suspend fun getMainPageData(): BaseResponse<String> {
        return request {
            delay(2000)
            return@request BaseResponse(200, "success", "主页数据")
        }
    }
}