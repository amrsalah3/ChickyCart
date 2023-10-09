package com.narify.ecommercy.data.products

import com.narify.ecommercy.model.entities.ProductEntity
import com.narify.ecommercy.model.entities.ProductsApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductApiDataSource @Inject constructor(private val client: HttpClient) {

    suspend fun getProducts(
        start: Int = 0,
        limit: Int = 5,
        category: String? = null
    ): ProductsApiResponse {
        val categoryPath = if (category != null) "/$CATEGORY_PATH_SEGMENT/$category" else ""
        val skipQuery = "$SKIP_QUERY=${start}"
        val limitQuery = "$LIMIT_QUERY=$limit"

        val response =
            client.get<ProductsApiResponse>("$BASE_URL$categoryPath?$skipQuery&$limitQuery")

        return response
    }

    suspend fun getProduct(id: String): ProductEntity {
        val response = client.get<ProductEntity>("$BASE_URL/$id")
        return response
    }

    companion object {
        private const val BASE_URL = "https://dummyjson.com/products"
        private const val CATEGORY_PATH_SEGMENT = "category"
        private const val SKIP_QUERY = "skip"
        private const val LIMIT_QUERY = "limit"
    }
}

