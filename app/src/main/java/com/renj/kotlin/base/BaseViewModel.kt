package com.renj.kotlin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renj.kotlin.http.error.AddressError
import com.renj.kotlin.http.error.ServiceError
import com.renj.kotlin.utils.Logger
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
    fun launchUI(
        block: suspend () -> Unit,
        addressError: (AddressError) -> Unit? = { e -> e.message?.let { Logger.e(it) } },
        serviceError: (ServiceError) -> Unit? = { e -> e.message?.let { Logger.e(it) } },
        otherError: (Error) -> Unit? = { e -> e.message?.let { Logger.e(it) } },
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: AddressError) {
                addressError(e)
            } catch (e: ServiceError) {
                serviceError(e)
            } catch (e: Error) {
                otherError(e)
            }
        }
    }
}