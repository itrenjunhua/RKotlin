package com.renj.kotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

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
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    CoroutineScope by MainScope() {

    protected lateinit var binding: DB
    protected var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = createViewModel(getViewModelClass())

        initViewAndData()
        initListener()
        initDataCallback()
    }

    protected open fun <T : ViewModel> createViewModel(modelClass: Class<T>?): T? {
        return modelClass?.let {
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[modelClass]
        }
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun getViewModelClass(): Class<VM>?

    protected abstract fun initViewAndData()

    protected open fun initListener() {
    }

    protected abstract fun initDataCallback()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}