package com.renj.kotlin.http.error

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-30   16:35
 *
 * 描述：地址错误 400
 *
 * 修订历史：
 *
 * ======================================================================
 */
class AddressError : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}