package com.renj.kotlin.base

import com.renj.kotlin.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-29   15:33
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
open class BaseRepository {
    suspend fun <T> request(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.also {
            Logger.i("BaseResponse[code=${it.code} , msg=${it.msg}]")
        }
    }
}