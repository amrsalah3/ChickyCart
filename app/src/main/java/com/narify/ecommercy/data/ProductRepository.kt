package com.narify.ecommercy.data

import com.narify.ecommercy.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productsDataSource: ProductsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getProducts(): List<Product> = withContext(ioDispatcher) {
        val products = productsDataSource.getProducts()
        // TODO: Handle errors
        return@withContext products
    }

    fun getProductStream(productId: String): Flow<Product?> {
        val product = productsDataSource.getProductStream(productId)
        // TODO: Handle errors
        return product
    }

    fun getProductsStream(): Flow<List<Product>> {
        val products = productsDataSource.getProductsStream()
        // TODO: Handle errors
        return products
    }
}
