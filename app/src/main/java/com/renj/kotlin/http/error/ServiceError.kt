package com.renj.kotlin.http.error

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-30   16:35
 *
 * 描述：服务器错误 500
 *
 * 修订历史：
 *
 * ======================================================================
 */
class ServiceError : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}