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
        return@withContext productsDataSource.getProducts()
    }

    fun getProductStream(productId: String): Flow<Product?> {
        return productsDataSource.getProductStream(productId)
    }

    fun getProductsStream(): Flow<List<Product>> {
        return productsDataSource.getProductsStream()
    }
}
