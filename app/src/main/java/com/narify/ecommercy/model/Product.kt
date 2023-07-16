package com.narify.ecommercy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
