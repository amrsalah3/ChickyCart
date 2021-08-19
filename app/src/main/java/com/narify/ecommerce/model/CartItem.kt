package com.narify.ecommerce.model

data class CartItem(val product: Product, var count: Int = 1)