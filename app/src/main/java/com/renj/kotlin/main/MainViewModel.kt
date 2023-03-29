package com.renj.kotlin.main

import androidx.lifecycle.MutableLiveData
import com.renj.kotlin.base.BaseViewModel

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2023-03-29   14:26
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class MainViewModel : BaseViewModel() {
    private val repository by lazy {
        MainRepository()
    }

    val mainData = MutableLiveData<String?>()

    fun getMainPageData() {
        launchUI {
            mainData.value = repository.getMainPageData().data
        }
    }
}