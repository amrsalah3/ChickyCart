package com.narify.chickycart.ui.common

import androidx.annotation.StringRes
import com.narify.chickycart.R

/**
 * Error state holding values for error ui
 */
data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMsgResId: Int = R.string.empty_string
)
