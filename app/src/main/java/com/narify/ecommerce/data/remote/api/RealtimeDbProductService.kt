package com.narify.ecommerce.data.remote.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.narify.ecommerce.model.Product
import com.narify.ecommerce.util.Constants
import timber.log.Timber

class RealtimeDbProductService : ProductApi {
    override fun getProducts(sortOption: String): LiveData<List<Product>> {
        val productsLiveData = MutableLiveData<List<Product>>()

        Firebase.database.reference.child("products").orderByChild(sortOption).get()
            .addOnSuccessListener { snapshot ->
                val products = ArrayList<Product>()
                snapshot.children.forEach { child ->
                    child.getValue<Product>()?.let {
                        it.id = child.key
                        products.add(it)
                    }
                }
                if (sortOption == Constants.FIREBASE_PRODUCT_RATING) products.reverse()
                productsLiveData.postValue(products)
            }
            .addOnFailureListener {
                Timber.d("GeneralLogKey getProducts: ${it.message}")
            }

        return productsLiveData
    }


}