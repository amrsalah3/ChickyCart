package com.narify.ecommercy.model

import com.narify.ecommercy.model.entities.ProductEntity

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val images: List<String>,
    val category: Category,
    val price: Price,
    val stock: Int,
    val rating: Rating,
) {
    val thumbnail: String = images[0]
}

data class Price(
    val value: Double,
    val original: Double,
    val currency: String,
    val symbol: String,
    val discount: Discount? = null
) {
    val raw: String = "$symbol${value.toInt()}"
}

data class Discount(
    val percentage: Int,
    val active: Boolean
) {
    val raw: String = percentage.toString()
}

data class Rating(val stars: Float = 0.0F, val raters: Int = 0)

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id.toString(),
        name = title,
        description = description,
        images = images,
        category = Category(id = category, name = category),
        price = Price(
            value = price,
            original = price,
            currency = currency,
            symbol = symbol,
            discount = Discount(
                percentage = discountPercentage.toInt(),
                active = isDiscountActive
            )
        ),
        stock = stock,
        rating = Rating(stars = rating.toFloat(), raters = raters)
    )
}
