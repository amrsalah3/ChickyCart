package com.narify.ecommercy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val id: String, val name: String) : Parcelable
