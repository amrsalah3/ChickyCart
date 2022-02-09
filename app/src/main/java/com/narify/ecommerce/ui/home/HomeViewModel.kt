package com.narify.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.narify.ecommerce.data.repository.ProductRepository
import com.narify.ecommerce.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private lateinit var productsLiveData: LiveData<List<Product>>

    private val _productsLoading = MutableLiveData<Boolean>().apply { value = false }
    val productsLoading get() = _productsLoading

    private fun getProducts() {
        _productsLoading.value = true
        //productRepository.getProducts()
        //ProductRepository().getProductsByName("The Lost Ways (HardCover special edition)")
    }


}
