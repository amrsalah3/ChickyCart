package com.narify.ecommercy.ui.productdetails

import androidx.annotation.StringRes
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.ui.common.ErrorState

data class ProductDetailsUiState(
    val isLoading: Boolean,
    val product: Product? = null,
    @StringRes val userMessage: Int? = null,
    val errorState: ErrorState = ErrorState()
)
