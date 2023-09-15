package com.narify.ecommercy.data.categories

import com.narify.ecommercy.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CategoriesDataSource {
    suspend fun getCategories(): List<Category>
    fun getCategoriesStream(): Flow<List<Category>>
}

class FakeCategoriesDataSource @Inject constructor() : CategoriesDataSource {
    override suspend fun getCategories(): List<Category> {
        delay(1000)
        val categories = List(10) { i ->
            Category("$i", "Category #$i")
        }
        return categories
    }

    override fun getCategoriesStream(): Flow<List<Category>> = flow {
        delay(1000)
        val categories = List(10) { i ->
            Category("$i", "Category #$i")
        }
        emit(categories)
    }

    fun getPreviewCategories(): List<Category> {
        return List(10) { i ->
            Category("$i", "Category #$i")
        }
    }
}
