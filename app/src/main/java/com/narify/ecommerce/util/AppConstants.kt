package com.narify.ecommerce.util

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AppConstants {
    companion object {
        const val REQUEST_CODE_SEARCH_FILTERS = "sort_filters_request_code"
        const val KEY_SORT_OPTION = "sort_option"

        // Firebase
        val FIREBASE_DB = Firebase.database.reference
        const val FIREBASE_PRODUCT_TITLE = "title"
        const val FIREBASE_PRODUCT_PRICE = "price/value"
        const val FIREBASE_PRODUCT_RATING = "rating/stars"
    }
}