package com.renj.kotlin.base

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-29   15:35
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
open class BaseResponse<T> {
    var errorCode: Int = 0
    var errorMsg: String? = ""
    var data: T? = null
}
