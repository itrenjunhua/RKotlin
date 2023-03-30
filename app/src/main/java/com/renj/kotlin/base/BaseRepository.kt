package com.renj.kotlin.base

import com.renj.kotlin.api.ApiService
import com.renj.kotlin.http.RetrofitUtil
import com.renj.kotlin.http.error.AddressError
import com.renj.kotlin.http.error.ServiceError
import com.renj.kotlin.utils.UIUtils
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
    protected val apiService by lazy {
        RetrofitUtil.newInstance().getApiService(ApiService::class.java)
    }

    suspend fun <T : BaseResponse<*>> requestHttp(call: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.also {
            if (!it.errorMsg.isNullOrBlank()) {
                UIUtils.showToast(it.errorMsg)
            }
            val errorCodeString = it.errorCode.toString()
            if (errorCodeString.contains("40")) throw AddressError("地址错误")
            if (errorCodeString.contains("50")) throw ServiceError("服务器错误")
        }
    }
}