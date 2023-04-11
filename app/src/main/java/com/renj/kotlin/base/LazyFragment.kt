package com.renj.kotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.renj.kotlin.utils.Logger

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 *
 *
 * 创建时间：2021-10-13   11:06
 *
 *
 * 描述：懒加载 Fragment，兼容 add()、replace()、ViewPager中使用形式
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
abstract class LazyFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment<DB, VM>() {
    private val TAG = javaClass.simpleName

    // -1：初始化未完成 0：初始化完成 1：第一次对用户可见 2：第一次对用户不可见 3：对用户可见  4：对用户不可见
    private var currentFragment = FRAGMENT_STATUS_UN_INIT

    // onHiddenChanged() 或者 setUserVisibleHint() 方法当前状态是否为对用户可见状态
    private var hiddenAndVisibleStatusVisible = true
    override fun structureMethod() {
        super.structureMethod()
        Logger.i("$TAG structureMethod ============= ")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.i("$TAG onAttach ============= ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("$TAG onCreate ============= ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.i("$TAG onCreateView ============= ")
        currentFragment = FRAGMENT_STATUS_UN_INIT
        val view = super.onCreateView(inflater, container, savedInstanceState)
        currentFragment = FRAGMENT_STATUS_INIT_FINISH
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.i("$TAG onActivityCreated ============= ")
    }

    override fun onResume() {
        super.onResume()
        Logger.i("$TAG onResume ============= $currentFragment -- $hiddenAndVisibleStatusVisible")
        if (hiddenAndVisibleStatusVisible) {
            if (currentFragment == FRAGMENT_STATUS_INIT_FINISH) {
                userFirstVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_VISIBLE)
                currentFragment = FRAGMENT_STATUS_FIRST_VISIBLE
            } else if (currentFragment == FRAGMENT_STATUS_FIRST_INVISIBLE || currentFragment == FRAGMENT_STATUS_INVISIBLE || currentFragment == FRAGMENT_STATUS_VISIBLE) {
                userVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_VISIBLE)
                currentFragment = FRAGMENT_STATUS_VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Logger.i("$TAG onPause ============= $currentFragment -- $hiddenAndVisibleStatusVisible")
        if (hiddenAndVisibleStatusVisible) {
            if (currentFragment == FRAGMENT_STATUS_FIRST_VISIBLE) {
                userFirstInVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_INVISIBLE)
                currentFragment = FRAGMENT_STATUS_INVISIBLE
            } else if (currentFragment == FRAGMENT_STATUS_VISIBLE
                || currentFragment == FRAGMENT_STATUS_INVISIBLE
            ) {
                userInVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_INVISIBLE)
                currentFragment = FRAGMENT_STATUS_INVISIBLE
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Logger.i("$TAG onHiddenChanged ============= hidden: $hidden")
        hiddenAndVisibleStatusVisible = !hidden
        if (!hidden) {
            if (currentFragment == FRAGMENT_STATUS_INIT_FINISH) {
                userFirstVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_VISIBLE)
                currentFragment = FRAGMENT_STATUS_FIRST_VISIBLE
            } else if (currentFragment == FRAGMENT_STATUS_FIRST_INVISIBLE
                || currentFragment == FRAGMENT_STATUS_INVISIBLE
            ) {
                userVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_VISIBLE)
                currentFragment = FRAGMENT_STATUS_VISIBLE
            }
        } else {
            if (currentFragment == FRAGMENT_STATUS_FIRST_VISIBLE) {
                userFirstInVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_INVISIBLE)
                currentFragment = FRAGMENT_STATUS_FIRST_INVISIBLE
            } else if (currentFragment == FRAGMENT_STATUS_VISIBLE) {
                userInVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_INVISIBLE)
                currentFragment = FRAGMENT_STATUS_INVISIBLE
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        hiddenAndVisibleStatusVisible = isVisibleToUser
        Logger.i("$TAG setUserVisibleHint ============= isVisibleToUser: $isVisibleToUser")
        if (isVisibleToUser) {
            if (currentFragment == FRAGMENT_STATUS_INIT_FINISH) {
                userFirstVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_VISIBLE)
                currentFragment = FRAGMENT_STATUS_FIRST_VISIBLE
            } else if (currentFragment == FRAGMENT_STATUS_FIRST_INVISIBLE
                || currentFragment == FRAGMENT_STATUS_INVISIBLE
            ) {
                userVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_VISIBLE)
                currentFragment = FRAGMENT_STATUS_VISIBLE
            }
        } else {
            if (currentFragment == FRAGMENT_STATUS_FIRST_VISIBLE) {
                userFirstInVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_FIRST_INVISIBLE)
                currentFragment = FRAGMENT_STATUS_FIRST_INVISIBLE
            } else if (currentFragment == FRAGMENT_STATUS_VISIBLE) {
                userInVisible()
                notifyChildHiddenChange(FRAGMENT_STATUS_INVISIBLE)
                currentFragment = FRAGMENT_STATUS_INVISIBLE
            }
        }
    }

    /**
     * 用户 **第一次可见，用于延迟加载数据**。非第一次可见时不会执行该方法，只会执行 [.userVisible]
     */
    fun userFirstVisible() {
        Logger.i("$TAG fistVisible ============= 用户第一次可见")
    }

    /**
     * 用户**非第一次可见**。第一次可见时不会执行该方法，只会执行 [.userFirstVisible]
     */
    fun userVisible() {
        Logger.i("$TAG visible ============= 用户可见")
    }

    /**
     * 用户**第一次不可见**。非第一次不可见时不会执行该方法，只会执行 [.userInVisible]
     */
    fun userFirstInVisible() {
        Logger.i("$TAG fistInVisible ============= 用户第一次不可见")
    }

    /**
     * 用户**非第一次不可见**。第一次不可见时不会执行该方法，只会执行 [.userFirstInVisible]
     */
    fun userInVisible() {
        Logger.i("$TAG inVisible ============= 用户不可见")
    }

    /**
     * 当自己的显示隐藏状态改变时，调用这个方法通知子Fragment
     *
     * @param fragmentStatus 状态
     */
    private fun notifyChildHiddenChange(fragmentStatus: Int) {
        if (isDetached || !isAdded) {
            return
        }
        val fragmentManager = childFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (fragment in fragments) {
            if (fragment is IParentVisibilityObserver) {
                val iParentVisibilityObserver = fragment as IParentVisibilityObserver
                if (fragmentStatus == FRAGMENT_STATUS_FIRST_VISIBLE) {
                    iParentVisibilityObserver.userParentFirstVisible()
                } else if (fragmentStatus == FRAGMENT_STATUS_VISIBLE) {
                    iParentVisibilityObserver.userParentVisible()
                } else if (fragmentStatus == FRAGMENT_STATUS_FIRST_INVISIBLE) {
                    iParentVisibilityObserver.userParentFirstInVisible()
                } else if (fragmentStatus == FRAGMENT_STATUS_INVISIBLE) {
                    iParentVisibilityObserver.userParentInVisible()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Logger.i("$TAG onStart ============= ")
    }

    override fun onStop() {
        super.onStop()
        Logger.i("$TAG onStop ============= ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.i("$TAG onDestroyView ============= ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("$TAG onDestroy ============= ")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.i("$TAG onDetach ============= ")
    }

    companion object {
        private const val FRAGMENT_STATUS_UN_INIT = -1
        private const val FRAGMENT_STATUS_INIT_FINISH = 0
        private const val FRAGMENT_STATUS_FIRST_VISIBLE = 1
        private const val FRAGMENT_STATUS_VISIBLE = 2
        private const val FRAGMENT_STATUS_FIRST_INVISIBLE = 3
        private const val FRAGMENT_STATUS_INVISIBLE = 4
    }
}