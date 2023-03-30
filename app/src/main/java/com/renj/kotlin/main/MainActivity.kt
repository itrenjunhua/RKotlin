package com.renj.kotlin.main

import com.renj.kotlin.R
import com.renj.kotlin.base.BaseActivity
import com.renj.kotlin.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initViewAndData() {
        viewModel?.getMainPageData(1)
    }

    override fun initDataCallback() {
        viewModel?.mainData?.observe(this) {
            binding.tvContent.text = it
        }
    }

    override fun initListener() {

    }
}