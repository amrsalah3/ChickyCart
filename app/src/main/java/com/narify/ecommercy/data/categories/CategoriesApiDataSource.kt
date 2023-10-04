package com.narify.ecommercy.data.categories

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesApiDataSource @Inject constructor(private val client: HttpClient) {

    suspend fun getCategories(): List<String> {
        val response =
            client.get<List<String>>("$BASE_URL/$CATEGORIES_PATH_SEGMENT")

        return response
    }

    companion object {
        private const val BASE_URL = "https://dummyjson.com/products"
        private const val CATEGORIES_PATH_SEGMENT = "categories"
    }
}

