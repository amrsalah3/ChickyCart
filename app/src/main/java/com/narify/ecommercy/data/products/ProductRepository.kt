package com.narify.ecommercy.data.products

import com.narify.ecommercy.model.Product
import com.narify.ecommercy.util.ProductsSortType
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

    /**
     * Retrieves a stream of products filtered by category and sorted by the specified criteria.
     *
     * @param category The category name to filter products by. If null, all products are included.
     * @param sortType The sorting criteria for the products.
     * @return A [Flow] emitting a list of products that match the specified category and sorting criteria.
     */
    fun getProductsStream(
        category: String? = null,
        sortType: ProductsSortType = ProductsSortType.NONE
    ): Flow<List<Product>> {

        // Map the enum value to the corresponding string value used by the data source for sorting.
        val mappedSortType = when (sortType) {
            ProductsSortType.ALPHABETICAL -> FakeProductsDataSource.SortType.ALPHABETICAL
            ProductsSortType.PRICE -> FakeProductsDataSource.SortType.PRICE
            ProductsSortType.RATING -> FakeProductsDataSource.SortType.RATING
            ProductsSortType.NONE -> FakeProductsDataSource.SortType.NONE
        }

        return productsDataSource.getProductsStream(category, mappedSortType)
    }

    fun getProductStream(productId: String): Flow<Product?> {
        return productsDataSource.getProductStream(productId)
    }
}
