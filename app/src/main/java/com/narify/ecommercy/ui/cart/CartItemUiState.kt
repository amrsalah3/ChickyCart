package com.narify.ecommercy.ui.cart

data class CartUiState(
    val productItems: List<CartProductItemUiState>
)

data class CartProductItemUiState(
    val name: String,
    val imageUrl: String,
    val count: Int,
    val totalPriceText: String
)
