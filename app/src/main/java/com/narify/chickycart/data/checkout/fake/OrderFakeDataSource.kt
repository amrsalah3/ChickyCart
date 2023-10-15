package com.narify.chickycart.data.checkout.fake

import com.narify.chickycart.model.Order
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
