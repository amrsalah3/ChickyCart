package com.narify.chickycart.data.categories

import com.narify.chickycart.model.Category
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
    private val dataSource: CategoriesApiMockDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoriesRepository {

    override suspend fun getCategories(): List<Category> = withContext(dispatcher) {
        return@withContext dataSource.getCategories()
    }

    override fun getCategoriesStream(): Flow<List<Category>> = flow {
        emit(getCategories())
    }.flowOn(dispatcher)
}

