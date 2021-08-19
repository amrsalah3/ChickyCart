package com.narify.ecommerce.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.narify.ecommerce.model.Product
import com.narify.ecommerce.util.AppConstants.Companion.FIREBASE_PRODUCT_RATING
import timber.log.Timber

class ProductRepository {

    fun getAllProducts(sortOption: String): LiveData<List<Product>> {
        val productsLiveData = MutableLiveData<List<Product>>()

        Firebase.database.reference.child("products").orderByChild(sortOption).get()
            .addOnSuccessListener { snapshot ->
                val products = ArrayList<Product>()
                for (snap in snapshot.children) {
                    snap.getValue<Product>()?.let {
                        it.id = snap.key
                        products.add(it)
                    }
                }
                if (sortOption == FIREBASE_PRODUCT_RATING) products.reverse()
                productsLiveData.postValue(products)
            }
            .addOnFailureListener {
                Timber.d("GeneralLogKey getProducts: ${it.message}")
            }

        return productsLiveData
    }

    fun getProductsByName(name: String): LiveData<List<Product>> {
        val productsLiveData = MutableLiveData<List<Product>>()

        Firebase.database.reference.child("products").orderByChild("title").equalTo(name).get()
            .addOnSuccessListener {
                val products = ArrayList<Product>()
                for (snap in it.children) {
                    val product = snap.getValue<Product>()
                    if (product != null) products.add(product)
                }
                productsLiveData.postValue(products)
            }
            .addOnFailureListener {
                Timber.d("GeneralLogKey getProducts: ${it.message}")
            }

        return productsLiveData
    }

}