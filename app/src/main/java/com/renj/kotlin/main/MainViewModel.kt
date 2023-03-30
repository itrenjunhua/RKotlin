package com.renj.kotlin.main

import androidx.lifecycle.MutableLiveData
import com.renj.kotlin.base.BaseViewModel
import com.renj.kotlin.kt.string

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

    fun getMainPageData(pageNo: Int) {
        launchUI(
            block = suspend {
                mainData.value = repository.getMainPageData(pageNo).data?.datas?.string {
                    "\n标题：${it.title} \n 作者：${it.chapterName}"
                }
            },
            addressError = {
                it.message?.run {
                    mainData.value = this
                }
            },
            serviceError = {
                it.message?.run {
                    mainData.value = this
                }
            }
        )
    }
}