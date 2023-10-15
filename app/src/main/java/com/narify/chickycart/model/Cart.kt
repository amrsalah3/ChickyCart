package com.narify.chickycart.model

import kotlin.math.min

data class CartItem(val product: Product, var count: Int)

val CartItem.totalPrice get() = product.price.value.toInt().times(count)
val CartItem.totalPriceText get() = "${product.price.symbol}$totalPrice"

class Cart(initialItems: List<CartItem> = emptyList()) {

    private val _items = ArrayDeque(initialItems.map { it.copy() })
    val items: List<CartItem> get() = _items

    fun addProduct(product: Product, count: Int = 1) {
        val cartItem = findProductCartItem(product.id)
        if (cartItem == null) _items.addFirst(CartItem(product, count))
        else cartItem.count += count
    }

    /**
     * Decreases cart product item count
     *
     * @param id product id
     * @param count number of product items to remove from the cart
     */
    fun removeProduct(id: String, count: Int = 1) {
        findProductCartItem(id)?.let { cartItem ->
            cartItem.count -= min(cartItem.count, count)
            if (cartItem.count == 0) _items.remove(cartItem)
        }
    }

    /**
     * Searches for cart product item by the id of the the product
     *
     * @param id product id
     */
    private fun findProductCartItem(id: String): CartItem? {
        return _items.find { item -> item.product.id == id }
    }
}

