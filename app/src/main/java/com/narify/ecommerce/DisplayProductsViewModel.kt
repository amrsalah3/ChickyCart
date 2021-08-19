package com.narify.ecommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DisplayProductsViewModel : ViewModel() {

    private val sortOptionLiveData = MutableLiveData<Int>()
    val selectedSort: LiveData<Int> get() = sortOptionLiveData

    fun setSortOption(option: Int) {
        sortOptionLiveData.value = option
    }

}