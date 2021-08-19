package com.narify.ecommerce.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val id: String? = null, val name: String? = null) : Parcelable
