package com.narify.ecommercy.ui.cart

import com.narify.ecommercy.ErrorState
import com.narify.ecommercy.model.CartItem

data class CartUiState(
    val isLoading: Boolean,
    val cartItems: List<CartItem> = emptyList(),
    val errorState: ErrorState = ErrorState()
)
