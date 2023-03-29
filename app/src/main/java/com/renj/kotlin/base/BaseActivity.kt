package com.renj.kotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
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

    protected fun <T : ViewModel> createViewModel(modelClass: Class<T>?): T? {
        return modelClass?.let {
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[modelClass]
        }
    }

    protected abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<VM>?

    abstract fun initViewAndData()

    protected open fun initListener(){
    }

    abstract fun initDataCallback()
}