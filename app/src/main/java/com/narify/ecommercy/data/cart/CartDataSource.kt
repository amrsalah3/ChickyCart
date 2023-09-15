package com.narify.ecommercy.data.cart

import com.narify.ecommercy.data.products.FakeProductsDataSource
import com.narify.ecommercy.model.Cart
import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface CartDataSource {
    suspend fun getCartItems(): List<CartItem>
    suspend fun increaseItemCount(product: Product)
    suspend fun decreaseItemCount(productId: String)
    fun getCartItemsStream(): Flow<List<CartItem>>
}

@Singleton
class FakeCartDataSource @Inject constructor() : CartDataSource {

    private val cartItems = MutableStateFlow(emptyList<CartItem>())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val products = FakeProductsDataSource().getProducts()
            val cart = Cart().apply {
                products.forEach { addProduct(it) }
            }
            cartItems.value = cart.items
        }
    }

    override suspend fun getCartItems(): List<CartItem> {
        delay(1000)
        val products = FakeProductsDataSource().getProducts()
        val cart = Cart().apply {
            products.forEach { addProduct(it) }
        }
        return cart.items
    }

    override fun getCartItemsStream(): Flow<List<CartItem>> = cartItems

    override suspend fun increaseItemCount(product: Product) {
        val cart = Cart(cartItems.value)
        cart.addProduct(product)
        cartItems.value = cart.items
    }

    override suspend fun decreaseItemCount(productId: String) {
        val cart = Cart(cartItems.value)
        cart.removeProduct(productId)
        cartItems.value = cart.items
    }

    fun getPreviewCartItems(): List<CartItem> {
        val products = FakeProductsDataSource().getPreviewProducts()
        val cart = Cart().apply {
            products.forEach { addProduct(it) }
        }
        return cart.items
    }
}
