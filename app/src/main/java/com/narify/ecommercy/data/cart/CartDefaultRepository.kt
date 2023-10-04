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
class CartDefaultRepository @Inject constructor(
    private val dataSource: CartLocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CartRepository {

    override suspend fun addProductToCart(product: Product) = withContext(dispatcher) {
        dataSource.addItemToCart(product)
    }

    override suspend fun removeProductFromCart(productId: String) = withContext(dispatcher) {
        dataSource.removeItemFromCart(productId)
    }

    override suspend fun getCartItems(): List<CartItem> = withContext(dispatcher) {
        return@withContext dataSource.getCartItems()
    }

    override fun getCartItemsStream(): Flow<List<CartItem>> = dataSource.getCartItemsStream()
}
