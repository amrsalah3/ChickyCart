package com.narify.ecommerce.data.local

import com.narify.ecommerce.model.Cart

interface DataManager {
    fun getProductSortOption(): String

    fun saveProductSortOption(sortOption: String)

    fun getCart(): Cart

    fun saveCart(cart: Cart)

    fun clearAll()
}
