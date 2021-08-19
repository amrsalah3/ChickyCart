package com.narify.ecommerce.data.remote.amazon

data class AmazonProduct(
    val asin: String,
    val brand: String,
    val categories: List<AmazonCategory>,
    val image: String,
    val is_amazon_fresh: Boolean,
    val is_prime: Boolean,
    val is_whole_foods_market: Boolean,
    val link: String,
    val position: Int,
    val rating: Double,
    val ratings_total: Long,
    val sponsored: Boolean,
    val title: String,
    val price: AmazonPrice
)

data class AmazonCategory(
    val id: String,
    val name: String
)

data class AmazonPrice(
    val originalValue: Double,
    val value: Double,
    val currency: String,
    val symbol: String,
    val raw: String
)