package com.narify.ecommercy.ui.cart

import com.narify.ecommercy.model.CartItem

data class CartUiState(
    val isLoading: Boolean,
    val cartItems: List<CartItem> = emptyList(),
    val userMessage: String? = null
)
