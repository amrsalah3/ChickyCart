package com.narify.ecommerce.model

class Cart {

    private val cart: ArrayDeque<CartItem> = ArrayDeque()

    fun getItems(): List<CartItem> = cart

    fun addProduct(product: Product, count: Int = 1) {
        val cartItem = getProductCartItem(product)
        if (cartItem == null) cart.addFirst(CartItem(product, count))
        else cartItem.count += count
    }

    private fun getProductCartItem(product: Product): CartItem? =
        cart.find { it.product.id == product.id }


}