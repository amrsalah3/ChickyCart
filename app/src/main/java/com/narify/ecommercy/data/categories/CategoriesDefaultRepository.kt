package com.narify.ecommercy.data.categories

import com.narify.ecommercy.model.Category
import com.narify.ecommercy.model.createCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesDefaultRepository @Inject constructor(
    private val dataSource: CategoriesApiDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoriesRepository {

    override suspend fun getCategories(): List<Category> = withContext(dispatcher) {
        val categoryNames = dataSource.getCategories()
        val categories = categoryNames.map { createCategory(it) }
        return@withContext categories
    }

    override fun getCategoriesStream(): Flow<List<Category>> = flow {
        emit(getCategories())
    }.flowOn(dispatcher)
}

