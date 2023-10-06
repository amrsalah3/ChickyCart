package com.narify.ecommercy.data.cart

import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CartRepository {
    fun getCartItemsStream(): Flow<List<CartItem>>
    suspend fun getCartItems(): List<CartItem>
    suspend fun addProductToCart(product: Product)
    suspend fun removeProductFromCart(productId: String)
    suspend fun clearCart()
}
