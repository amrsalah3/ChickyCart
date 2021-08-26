package com.narify.ecommerce.ui.cart

import androidx.lifecycle.ViewModel
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val pref: AppPreferences) : ViewModel() {

    fun addItem(product: Product) {
        val cart = pref.getCart()
        cart.addProduct(product)
        pref.saveCart(cart)
    }

    fun removeItem(product: Product) {
        val cart = pref.getCart()
        cart.removeProduct(product)
        pref.saveCart(cart)
    }

    fun getItems() = pref.getCart().getItems()

}