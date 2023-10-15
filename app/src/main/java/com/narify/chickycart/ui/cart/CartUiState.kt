package com.narify.chickycart.ui.cart

import com.narify.chickycart.model.CartItem
import com.narify.chickycart.ui.common.ErrorState

data class CartUiState(
    val isLoading: Boolean,
    val cartItems: List<CartItem> = emptyList(),
    val errorState: ErrorState = ErrorState()
)
