package com.narify.ecommercy.data.cart

import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDataSource: CartDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCartItems(): List<CartItem> = withContext(dispatcher) {
        return@withContext cartDataSource.getCartItems()
    }

    fun getCartItemsStream(): Flow<List<CartItem>> {
        return cartDataSource.getCartItemsStream()
    }

    suspend fun addProductToCart(product: Product) = withContext(dispatcher) {
        cartDataSource.increaseItemCount(product)
    }

    suspend fun removeProductFromCart(productId: String) = withContext(dispatcher) {
        cartDataSource.decreaseItemCount(productId)
    }
}

