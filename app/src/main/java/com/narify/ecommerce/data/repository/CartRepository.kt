package com.narify.ecommerce.data.repository

import com.narify.ecommerce.data.local.DataManager
import com.narify.ecommerce.model.CartItem
import com.narify.ecommerce.model.Product
import com.narify.ecommerce.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CartRepository @Inject constructor(
    private val mgr: DataManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getItems(): Result<List<CartItem>> = withContext(ioDispatcher) {
        val items = mgr.getCart().getItems()
        return@withContext Result.Success(items)
    }

    suspend fun addItem(product: Product) = withContext(ioDispatcher) {
        val cart = mgr.getCart()
        cart.addProduct(product)
        mgr.saveCart(cart)
    }

    suspend fun removeItem(product: Product) = withContext(ioDispatcher) {
        val cart = mgr.getCart()
        cart.removeProduct(product)
        mgr.saveCart(cart)
    }

}