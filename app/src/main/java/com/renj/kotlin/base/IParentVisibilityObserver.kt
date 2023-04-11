package com.renj.kotlin.base

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 *
 *
 * 创建时间：2021-10-14   10:06
 *
 *
 * 描述：当有多重嵌套时(比如 ViewPager 嵌套 ViewPager，再嵌套 Fragment)，
 * 宿主Fragment在生命周期执行的时候会相应的分发到子Fragment中，但是setUserVisibleHint()和onHiddenChanged()却没有进行相应的回调，
 * 如果子Fragment需要监听父Fragment的显示、隐藏状态时子Fragment通过实现该接口重写对应方法即可
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
interface IParentVisibilityObserver {
    /**
     * 父Fragment 用户 **第一次可见，用于延迟加载数据**。非第一次可见时不会执行该方法，只会执行 [.userParentVisible]
     */
    fun userParentFirstVisible()

    /**
     * 父Fragment 用户**非第一次可见**。第一次可见时不会执行该方法，只会执行 [.userParentFirstVisible]
     */
    fun userParentVisible()

    /**
     * 父Fragment 用户**第一次不可见**。非第一次不可见时不会执行该方法，只会执行 [.userParentInVisible]
     */
    fun userParentFirstInVisible()

    /**
     * 父Fragment 用户**非第一次不可见**。第一次不可见时不会执行该方法，只会执行 [.userParentFirstInVisible]
     */
    fun userParentInVisible()
}