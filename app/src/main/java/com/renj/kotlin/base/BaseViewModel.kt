package com.renj.kotlin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-28   17:06
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
open class BaseViewModel : ViewModel() {
    fun launchUI(block:suspend CoroutineScope.() -> Unit){
        viewModelScope.launch {
            block()
        }
    }
}