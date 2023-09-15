package com.narify.ecommercy.ui.cart

import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.ui.common.ErrorState

data class CartUiState(
    val isLoading: Boolean,
    val cartItems: List<CartItem> = emptyList(),
    val errorState: ErrorState = ErrorState()
)
