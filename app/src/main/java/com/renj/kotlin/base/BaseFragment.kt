package com.renj.kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 *
 *
 * 创建时间：2021-10-13   16:34
 *
 *
 * 描述：Fragment 基类，不是懒加载的 Fragment，如果需要使用懒加载请使用 [LazyFragment]
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    CoroutineScope by MainScope() {

    protected lateinit var binding: DB
    protected var viewModel: VM? = null


    init {
        structureMethod()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel(getViewModelClass())
        initDataCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeInitViewData()
        val rootView = inflater.inflate(layoutId, container, false)
        rootView?.let {
            binding = DataBindingUtil.bind(it)!!
        }

        initViewAndData(rootView, arguments)
        initListener(rootView)
        return rootView
    }

    protected open fun <T : ViewModel> createViewModel(modelClass: Class<T>?): T? {
        return modelClass?.let {
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            )[modelClass]
        }
    }

    /**
     * 构造方法中调用，在 onCreate() 方法之前执行
     */
    protected open fun structureMethod() {}

    /**
     * 在初始化View和Data之前调用
     */
    protected open fun beforeInitViewData() {}

    @get:LayoutRes
    protected abstract val layoutId: Int
    protected abstract fun initViewAndData(rootView: View?, arguments: Bundle?)
    protected open fun initListener(rootView: View?) {}

    protected open fun getViewModelClass(): Class<VM>? {
        return null
    }

    protected open fun initDataCallback() {}

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

}