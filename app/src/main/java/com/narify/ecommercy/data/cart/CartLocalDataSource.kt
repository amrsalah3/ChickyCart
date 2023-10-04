package com.narify.ecommercy.data.cart

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.narify.ecommercy.model.Cart
import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartLocalDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore by preferencesDataStore(
        name = PREF_FILE_NAME
    )
    private val dataStore = context.dataStore
    private val gson: Gson = Gson()

    fun getCartItemsStream(): Flow<List<CartItem>> = dataStore.data.map { pref ->
        val serializedCart: String? = pref[CART_KEY]
        return@map try {
            gson.fromJson(serializedCart, Cart::class.java).items
        } catch (e: Exception) {
            emptyList()
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getCartItems() = getCartItemsStream().first()

    suspend fun addItemToCart(product: Product) = withContext(Dispatchers.IO) {
        val savedItems = getCartItems()
        val cart = Cart(savedItems)
        cart.addProduct(product)
        saveCart(cart)
    }

    suspend fun removeItemFromCart(productId: String) = withContext(Dispatchers.IO) {
        val savedItems = getCartItems()
        val cart = Cart(savedItems)
        cart.removeProduct(productId)
        saveCart(cart)
    }

    suspend fun emptyCart() = dataStore.edit { pref -> pref.clear() }

    private suspend fun saveCart(cart: Cart) {
        val serializedCart = gson.toJson(cart)
        dataStore.edit { pref ->
            pref[CART_KEY] = serializedCart
        }
    }

    private companion object {
        private const val PREF_FILE_NAME = "cart_data_store"
        private val CART_KEY = stringPreferencesKey("cart_items")
    }
}
