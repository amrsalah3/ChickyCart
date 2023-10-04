package com.narify.ecommercy.data.cart

import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CartRepository {
    suspend fun addProductToCart(product: Product)
    suspend fun removeProductFromCart(productId: String)
    suspend fun getCartItems(): List<CartItem>
    fun getCartItemsStream(): Flow<List<CartItem>>
}
