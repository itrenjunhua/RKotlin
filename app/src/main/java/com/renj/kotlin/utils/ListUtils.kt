package com.renj.kotlin.utils

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-28   17:49
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
object ListUtils {
    fun <T> isEmpty(list: List<T>?): Boolean {
        return (list == null) || list.isEmpty()
    }
}