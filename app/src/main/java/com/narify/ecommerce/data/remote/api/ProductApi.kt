package com.narify.ecommerce.data.remote.api

import androidx.lifecycle.LiveData
import com.narify.ecommerce.model.Product

interface ProductApi {
    fun getProducts(sortOption: String): LiveData<List<Product>>
}