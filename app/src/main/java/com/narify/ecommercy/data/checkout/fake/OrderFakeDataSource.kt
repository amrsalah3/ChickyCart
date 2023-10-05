package com.narify.ecommercy.data.checkout.fake

import com.narify.ecommercy.model.Order
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderFakeDataSource @Inject constructor() {

    suspend fun placeOrder(order: Order) {
        delay(1000)
        // Order placed. (Imagine)
        return
    }
}
