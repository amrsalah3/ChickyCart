package com.narify.ecommercy.data.categories.fake

import com.narify.ecommercy.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoriesFakeDataSource {

    suspend fun getCategories(): List<Category> {
        delay(1000)
        val categories = List(10) { i ->
            Category("$i", "Category $i")
        }
        return categories
    }

    fun getCategoriesStream(): Flow<List<Category>> = flow {
        delay(1000)
        val categories = List(10) { i ->
            Category("$i", "Category $i")
        }
        emit(categories)
    }

    fun getPreviewCategories(): List<Category> {
        return List(10) { i ->
            Category("$i", "Category $i")
        }
    }
}
