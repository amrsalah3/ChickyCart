package com.narify.ecommercy.data

import com.narify.ecommercy.model.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoriesDataSource: CategoriesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getCategories(): List<Category> = withContext(ioDispatcher) {
        return@withContext categoriesDataSource.getCategories()
    }

    fun getCategoriesStream(): Flow<List<Category>> {
        return categoriesDataSource.getCategoriesStream()
    }
}

