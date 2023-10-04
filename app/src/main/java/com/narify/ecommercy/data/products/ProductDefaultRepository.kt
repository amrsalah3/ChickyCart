package com.narify.ecommercy.data.products

import com.narify.ecommercy.model.Product
import com.narify.ecommercy.model.toProduct
import com.narify.ecommercy.util.ProductsSortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDefaultRepository @Inject constructor(
    private val dataSource: ProductApiDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getProducts(): List<Product> = withContext(dispatcher) {
        val response = dataSource.getProducts()
        val products = response.products.map { it.toProduct() }
        return@withContext products
    }

    override fun getProductsStream(
        category: String?,
        sortType: ProductsSortType
    ): Flow<List<Product>> = flow {
        // Get products from the network.
        val response = dataSource.getProducts(category)
        val products = response.products.map { it.toProduct() }

        // Apply the sort if specified.
        val sortedProducts = when (sortType) {
            ProductsSortType.ALPHABETICAL -> products.sortedBy { it.name }
            ProductsSortType.PRICE -> products.sortedBy { it.price.value }
            ProductsSortType.RATING -> products.sortedByDescending { it.rating.stars }
            else -> products
        }

        emit(sortedProducts)
    }.flowOn(dispatcher)

    override fun getProductStream(productId: String): Flow<Product?> = flow {
        val productEntity = dataSource.getProduct(productId)
        emit(productEntity.toProduct())
    }.flowOn(dispatcher)
}
