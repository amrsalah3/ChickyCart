package com.narify.ecommercy.model

import android.os.Parcelable
import com.narify.ecommercy.model.entities.ProductEntity
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Product(
    var id: String,
    val name: String,
    val description: String,
    val images: List<String>,
    val category: Category,
    val price: Price,
    val stock: Int,
    val rating: Rating,
) : Parcelable {

    fun getThumbnail(): String = images[0]
}

@Parcelize
data class Price(
    val value: Double,
    val original: Double,
    val currency: String,
    val symbol: String,
    val discount: Discount? = null,
    val raw: String
) : Parcelable

@Parcelize
data class Discount(
    val percentage: Int,
    val active: Boolean,
    val raw: String
) : Parcelable

@Parcelize
data class Rating(val stars: Float = 0.0F, val raters: Int = 0) : Parcelable

fun ProductEntity.toProduct(): Product {
    val currency = "USD"
    val symbol = "$"
    val raters = Random.nextInt(1, 10000)
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
                active = discountPercentage > 0,
                raw = discountPercentage.toString()
            ),
            raw = "$price $currency"
        ),
        stock = stock,
        rating = Rating(stars = rating.toFloat(), raters = raters)
    )
}
