package com.narify.ecommerce.data.remote.amazon

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RainforestApi {
    @GET("request?api_key=20B797BE5FBC4439A93110C56467CE92&type=category&amazon_domain=amazon.com")
    fun getProducts(@Query("category_id") categoryId: Long): Call<String>
}