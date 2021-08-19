package com.narify.ecommerce.data.remote.amazon

import android.content.Context

class AmazonToProductMapper {
    companion object {
        /*       @JvmStatic
               fun map(amazonProduct: AmazonProduct): Product {
                   with(amazonProduct) {
                       return Product(
                           imageUrl = image,
                           title = title,
                           price = "5000$",
                           rating = rating,
                           ratingsCount = 0
                       )
                   }
               }*/

        @JvmStatic
        fun readAssetsFile(fileName: String, context: Context): String =
            context.assets.open(fileName).bufferedReader().readText()

    }
}

