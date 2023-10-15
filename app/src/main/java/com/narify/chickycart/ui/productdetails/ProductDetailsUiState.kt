package com.narify.chickycart.ui.productdetails

import androidx.annotation.StringRes
import com.narify.chickycart.model.Product
import com.narify.chickycart.ui.common.ErrorState

data class ProductDetailsUiState(
    val isLoading: Boolean,
    val product: Product? = null,
    @StringRes val userMessage: Int? = null,
    val errorState: ErrorState = ErrorState()
)
