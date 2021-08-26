package com.narify.ecommerce.data.local

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.narify.ecommerce.model.Cart
import com.narify.ecommerce.util.AppConstants.Companion.FIREBASE_PRODUCT_TITLE
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppPreferences @Inject constructor(application: Application) {

    private val pref = application.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getProductSortOption(): String =
        pref.getString(PREF_KEY_SORT_OPTION, FIREBASE_PRODUCT_TITLE) ?: FIREBASE_PRODUCT_TITLE

    fun saveProductSortOption(sortOption: String) =
        pref.edit(commit = true) { putString(PREF_KEY_SORT_OPTION, sortOption) }


    fun getCart(): Cart {
        val encodedCart = pref.getString(PREF_KEY_CART, "") ?: ""
        return try {
            gson.fromJson(encodedCart, Cart::class.java)
        } catch (e: Exception) {
            Cart()
        }
    }

    fun saveCart(cart: Cart) {
        val encodedCart = gson.toJson(cart)
        pref.edit { putString(PREF_KEY_CART, encodedCart) }
    }


    fun clearAll() = pref.edit(commit = true) { clear() }

    private companion object {
        private const val PREF_FILE_NAME = "user_preferences"

        private const val PREF_KEY_SORT_OPTION = "product_sort_option"

        private const val PREF_KEY_CART = "cart_items"
    }
}