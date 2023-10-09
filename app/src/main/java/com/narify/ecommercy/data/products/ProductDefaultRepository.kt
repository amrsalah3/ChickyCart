package com.narify.ecommercy.data.products

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.narify.ecommercy.data.products.fake.ProductFakeDataSource
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.model.toProduct
import com.narify.ecommercy.util.ProductsSortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDefaultRepository @Inject constructor(
    private val dataSource: ProductApiMockDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override fun getProductsStream(
        searchQuery: String?,
        category: String?,
        sortType: ProductsSortType,
        featuredProductsOnly: Boolean,
    ): Flow<PagingData<Product>> {
        // Map the enum value to the corresponding string value used by the data source for sorting.
        val mappedSortType = when (sortType) {
            ProductsSortType.ALPHABETICAL -> ProductFakeDataSource.SortType.ALPHABETICAL
            ProductsSortType.PRICE -> ProductFakeDataSource.SortType.PRICE
            ProductsSortType.RATING -> ProductFakeDataSource.SortType.RATING
            ProductsSortType.NONE -> ProductFakeDataSource.SortType.NONE
        }

        return Pager(
            config = PagingConfig(pageSize = ProductApiMockDataSource.DEFAULT_LIMIT),
            pagingSourceFactory = {
                ProductsPagingSource(
                    dataSource = dataSource,
                    searchQuery = searchQuery,
                    categoryFilter = category,
                    sortType = mappedSortType,
                    featuredProductsOnly = featuredProductsOnly
                )
            }
        ).flow
            .onStart { delay(2000) }
            .map { pagingData -> pagingData.map { it.toProduct() } }
            .flowOn(dispatcher)
    }

    override fun getProductStream(productId: String): Flow<Product?> = flow {
        val productEntity = dataSource.getProduct(productId)
        emit(productEntity?.toProduct())
    }.flowOn(dispatcher)
}
