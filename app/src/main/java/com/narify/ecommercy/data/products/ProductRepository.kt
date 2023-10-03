package com.narify.ecommercy.data.products

import com.narify.ecommercy.model.Product
import com.narify.ecommercy.util.ProductsSortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface ProductRepository {

    suspend fun getProducts(): List<Product>

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
    ): Flow<List<Product>>

    fun getProductStream(productId: String): Flow<Product?>
}
