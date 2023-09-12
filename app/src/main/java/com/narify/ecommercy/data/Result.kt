package com.narify.ecommercy.data

import androidx.annotation.StringRes

sealed interface Result<out R> {

    data class Success<out T>(val data: T) : Result<T>
    data class Error(@StringRes val messageResId: Int, val exception: Exception? = null) :
        Result<Nothing>

    object Loading : Result<Nothing>
}
