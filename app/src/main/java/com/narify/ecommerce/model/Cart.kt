package com.narify.ecommerce.model

import kotlin.math.min

class Cart {

    private val cart: ArrayDeque<CartItem> = ArrayDeque()

    fun getItems(): List<CartItem> = cart

    fun addProduct(product: Product, count: Int = 1) {
        val cartItem = getProductCartItem(product)
        if (cartItem == null) cart.addFirst(CartItem(product, count))
        else cartItem.count += count
    }

    fun removeProduct(product: Product, count: Int = 1) {
        val cartItem = getProductCartItem(product)
        if (cartItem != null) {
            cartItem.count -= min(cartItem.count, count)
            if (cartItem.count == 0) cart.remove(cartItem)
        }
    }

    private fun getProductCartItem(product: Product): CartItem? =
        cart.find { it.product.id == product.id }


}