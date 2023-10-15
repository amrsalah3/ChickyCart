package com.narify.chickycart.data.products

import androidx.paging.PagingData
import com.narify.chickycart.model.Product
import com.narify.chickycart.util.ProductsSortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface ProductRepository {

    /**
     * Retrieves a stream of products filtered by category and sorted by the specified criteria.
     *
     * @param searchQuery The query string to search for matching products. If null, all products are included.
     * @param category The category name to filter products by. If null, all products are included.
     * @param sortType The sorting criteria for the products.
     * @param featuredProductsOnly Set it to true to get the featured products only.
     * @return A [Flow] emitting a [PagingData] of products that match the specified category and sorting criteria.
     */
    fun getProductsStream(
        searchQuery: String? = null,
        category: String? = null,
        sortType: ProductsSortType = ProductsSortType.NONE,
        featuredProductsOnly: Boolean = false,
    ): Flow<PagingData<Product>>

    fun getProductStream(productId: String): Flow<Product?>
}
