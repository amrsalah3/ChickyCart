package com.narify.ecommercy.data.products.fake

import com.narify.ecommercy.data.products.ProductRepository
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.util.ProductsSortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductFakeRepository @Inject constructor(
    private val dataSource: ProductFakeDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getProducts(): List<Product> = withContext(dispatcher) {
        return@withContext dataSource.getProducts()
    }

    override fun getProductsStream(
        category: String?,
        sortType: ProductsSortType
    ): Flow<List<Product>> {

        // Map the enum value to the corresponding string value used by the data source for sorting.
        val mappedSortType = when (sortType) {
            ProductsSortType.ALPHABETICAL -> ProductFakeDataSource.SortType.ALPHABETICAL
            ProductsSortType.PRICE -> ProductFakeDataSource.SortType.PRICE
            ProductsSortType.RATING -> ProductFakeDataSource.SortType.RATING
            ProductsSortType.NONE -> ProductFakeDataSource.SortType.NONE
        }

        return dataSource.getProductsStream(category, mappedSortType)
    }

    override fun getProductStream(productId: String): Flow<Product?> {
        return dataSource.getProductStream(productId)
    }
}
