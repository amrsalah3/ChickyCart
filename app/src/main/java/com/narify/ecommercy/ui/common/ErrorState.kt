package com.narify.ecommercy.ui.common

import androidx.annotation.StringRes
import com.narify.ecommercy.R

/**
 * Error state holding values for error ui
 */
data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMsgResId: Int = R.string.empty_string
)
