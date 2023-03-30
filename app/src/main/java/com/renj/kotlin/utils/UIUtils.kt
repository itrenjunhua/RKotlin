package com.renj.kotlin.utils

import android.widget.Toast

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-30   16:28
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
object UIUtils {
    private val toast by lazy {
        Toast.makeText(AndroidUtils.getApplication(), "", Toast.LENGTH_SHORT)
    }

    fun showToast(msg: String?) {
        msg?.let {
            toast.setText(msg)
            toast.show()
        }
    }
}