package com.narify.ecommerce.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @Exclude @JvmField var id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val images: List<String>? = null,
    val category: Category? = null,
    val price: Price? = null,
    val stock: Int? = null,
    val rating: Rating? = null,
) : Parcelable {
    @Exclude
    fun getThumbnail(): String? = images?.get(0)
}

@Parcelize
data class Price(
    val value: Double? = null,
    val original: Double? = null,
    val currency: String? = null,
    val symbol: String? = null,
    val discount: Discount? = null,
    val raw: String? = null
) : Parcelable

@Parcelize
data class Discount(
    val percentage: Int? = null,
    val active: Boolean? = null,
    val raw: String? = null
) : Parcelable

@Parcelize
data class Rating(val stars: Float? = null, val count: Int? = null) : Parcelable