package com.narify.chickycart.data.products

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.narify.chickycart.model.entities.ProductEntity

class ProductsPagingSource(
    private val dataSource: ProductApiMockDataSource,
    private val searchQuery: String? = null,
    private val categoryFilter: String? = null,
    private val sortType: String? = null,
    private val featuredProductsOnly: Boolean = false
) : PagingSource<Int, ProductEntity>() {

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            val currentPage = params.key ?: 0
            val size = params.loadSize
            val startAt = currentPage * size

            val productEntities = dataSource.getProducts(
                searchQuery = searchQuery,
                category = categoryFilter,
                sortType = sortType,
                featuredProductsOnly = featuredProductsOnly,
                startAt = startAt
            )

            val prevKey = if (currentPage > 0) currentPage - 1 else null
            val nextKey = if (productEntities.isNotEmpty()) currentPage + 1 else null

            LoadResult.Page(
                data = productEntities,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
