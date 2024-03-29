package com.narify.chickycart.data.cart.fake

import com.narify.chickycart.data.products.fake.ProductFakeDataSource
import com.narify.chickycart.model.Cart
import com.narify.chickycart.model.CartItem
import com.narify.chickycart.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartFakeDataSource @Inject constructor() {

    private val cartItems = MutableStateFlow(emptyList<CartItem>())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val products = ProductFakeDataSource().getProducts()
            val cart = Cart().apply {
                products.forEach { addProduct(it) }
            }
            cartItems.value = cart.items
        }
    }

    fun getCartItemsStream(): Flow<List<CartItem>> = cartItems

    suspend fun getCartItems(): List<CartItem> {
        delay(1000)
        val products = ProductFakeDataSource().getProducts()
        val cart = Cart().apply {
            products.forEach { addProduct(it) }
        }
        return cart.items
    }

    suspend fun increaseItemCount(product: Product) {
        val cart = Cart(cartItems.value)
        cart.addProduct(product)
        cartItems.value = cart.items
    }

    suspend fun decreaseItemCount(productId: String) {
        val cart = Cart(cartItems.value)
        cart.removeProduct(productId)
        cartItems.value = cart.items
    }

    suspend fun clearCart() {
        cartItems.value = emptyList()
    }

    fun getPreviewCartItems(): List<CartItem> {
        val products = ProductFakeDataSource().getPreviewProducts()
        val cart = Cart().apply {
            products.forEach { addProduct(it) }
        }
        return cart.items
    }
}
