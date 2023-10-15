package com.narify.chickycart.data.products.fake

import androidx.paging.PagingData
import com.narify.chickycart.data.products.ProductRepository
import com.narify.chickycart.model.Product
import com.narify.chickycart.util.ProductsSortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductFakeRepository @Inject constructor(
    private val dataSource: ProductFakeDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override fun getProductsStream(
        searchQuery: String?,
        category: String?,
        sortType: ProductsSortType,
        featuredProductsOnly: Boolean
    ): Flow<PagingData<Product>> {

        // Map the enum value to the corresponding string value used by the data source for sorting.
        val mappedSortType = when (sortType) {
            ProductsSortType.ALPHABETICAL -> ProductFakeDataSource.SortType.ALPHABETICAL
            ProductsSortType.PRICE -> ProductFakeDataSource.SortType.PRICE
            ProductsSortType.RATING -> ProductFakeDataSource.SortType.RATING
            ProductsSortType.NONE -> ProductFakeDataSource.SortType.NONE
        }

        return dataSource.getProductsStream(searchQuery, category, mappedSortType)
            .flowOn(dispatcher)
    }

    override fun getProductStream(productId: String): Flow<Product?> {
        return dataSource.getProductStream(productId).flowOn(dispatcher)
    }
}
