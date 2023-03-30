package com.renj.kotlin.base

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-30   15:51
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
open class BaseListResponse<T> : BaseResponse<BaseListResponse<T>>() {
    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: List<T>? = null
}
