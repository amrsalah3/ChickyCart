package com.narify.chickycart.data.cart

import com.narify.chickycart.model.CartItem
import com.narify.chickycart.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartDefaultRepository @Inject constructor(
    private val dataSource: CartLocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CartRepository {

    override fun getCartItemsStream(): Flow<List<CartItem>> = dataSource.getCartItemsStream()

    override suspend fun getCartItems(): List<CartItem> = withContext(dispatcher) {
        return@withContext dataSource.getCartItems()
    }

    override suspend fun addProductToCart(product: Product) = withContext(dispatcher) {
        dataSource.addItemToCart(product)
    }

    override suspend fun removeProductFromCart(productId: String) = withContext(dispatcher) {
        dataSource.removeItemFromCart(productId)
    }

    override suspend fun clearCart() {
        dataSource.clearCart()
    }
}
