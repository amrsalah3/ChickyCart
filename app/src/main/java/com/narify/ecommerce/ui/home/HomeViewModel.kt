package com.narify.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.narify.ecommerce.data.repository.ProductRepository
import com.narify.ecommerce.model.Product

class HomeViewModel : ViewModel() {
    private lateinit var productsLiveData: LiveData<List<Product>>

    init {
        loadProducts()
    }

    private fun loadProducts() {
        productsLiveData =
            ProductRepository().getProductsByName("The Lost Ways (HardCover special edition)")
    }

    fun getProducts(): LiveData<List<Product>> = productsLiveData
}
