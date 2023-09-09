package com.narify.ecommercy

import androidx.annotation.StringRes

/**
 * Error state holding values for error ui
 */
data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMsgStringRes: Int = R.string.empty_string
)
