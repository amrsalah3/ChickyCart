package com.narify.ecommercy.data.categories.fake

import com.narify.ecommercy.model.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesFakeRepository @Inject constructor(
    private val dataSource: CategoriesFakeDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getCategories(): List<Category> = withContext(dispatcher) {
        return@withContext dataSource.getCategories()
    }

    fun getCategoriesStream(): Flow<List<Category>> {
        return dataSource.getCategoriesStream()
    }
}
