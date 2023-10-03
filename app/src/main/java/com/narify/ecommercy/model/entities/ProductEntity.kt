package com.narify.ecommercy.model.entities

import kotlinx.serialization.Serializable

/**
 * Model class representing a network response from [com.narify.ecommercy.data.ProductApi]
 */
@Serializable
data class ProductsApiResponse(
    val products: List<ProductEntity>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

/**
 * Model class representing a single product of a network response from [com.narify.ecommercy.data.ProductApi]
 */
@Serializable
data class ProductEntity(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
