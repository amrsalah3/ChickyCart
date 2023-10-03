package com.narify.ecommercy.data.categories

import com.narify.ecommercy.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
    fun getCategoriesStream(): Flow<List<Category>>
}
