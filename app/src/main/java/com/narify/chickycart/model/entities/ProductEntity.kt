package com.narify.chickycart.model.entities

import kotlinx.serialization.Serializable
import kotlin.random.Random

/**
 * Model class representing a network response from [com.narify.chickycart.data.ProductApi]
 */
@Serializable
data class ProductsApiResponse(
    val products: List<ProductEntity>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

/**
 * Model class representing a single product of a network response from [com.narify.chickycart.data.ProductApi]
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
) {
    // Random boolean with a 20% chance of (true)
    val isDiscountActive = (Random.nextInt(5) == 0) && (discountPercentage > 0)

    // Random number of raters
    val raters = Random.nextInt(1, 10000)

    val currency = "USD"
    val symbol = "$"
    val priceBeforeDiscount = if (isDiscountActive) {
        price / (1.0 - discountPercentage / 100)
    } else {
        price
    }
}
