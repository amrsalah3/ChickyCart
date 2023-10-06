package com.narify.ecommercy.data.cart.fake

import com.narify.ecommercy.data.cart.CartRepository
import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartFakeRepository @Inject constructor(
    private val dataSource: CartFakeDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CartRepository {

    override fun getCartItemsStream(): Flow<List<CartItem>> {
        return dataSource.getCartItemsStream()
    }

    override suspend fun getCartItems(): List<CartItem> = withContext(dispatcher) {
        return@withContext dataSource.getCartItems()
    }

    override suspend fun addProductToCart(product: Product) = withContext(dispatcher) {
        dataSource.increaseItemCount(product)
    }

    override suspend fun removeProductFromCart(productId: String) = withContext(dispatcher) {
        dataSource.decreaseItemCount(productId)
    }

    override suspend fun clearCart() {
        dataSource.clearCart()
    }
}
