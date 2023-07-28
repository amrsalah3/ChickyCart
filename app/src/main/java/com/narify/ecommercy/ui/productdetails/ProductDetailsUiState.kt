package com.narify.ecommercy.ui.productdetails

import com.narify.ecommercy.model.Product

data class ProductDetailsUiState(
    val isLoading: Boolean,
    val product: Product? = null,
    val userMessage: String? = null
)
