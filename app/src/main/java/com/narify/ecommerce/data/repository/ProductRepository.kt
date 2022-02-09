package com.narify.ecommerce.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.narify.ecommerce.data.remote.api.ProductApi
import com.narify.ecommerce.model.Product
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val api: ProductApi) {

    fun getProducts(sortOption: String): LiveData<List<Product>> = api.getProducts(sortOption)


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