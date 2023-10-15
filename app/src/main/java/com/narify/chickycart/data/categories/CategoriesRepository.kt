package com.narify.chickycart.data.categories

import com.narify.chickycart.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
    fun getCategoriesStream(): Flow<List<Category>>
}
