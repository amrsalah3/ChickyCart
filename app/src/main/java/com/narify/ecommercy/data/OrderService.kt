package com.narify.ecommercy.data

import com.narify.ecommercy.model.Order
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface OrderService {
    suspend fun placeOrder(order: Order)
}

@Singleton
class FakeOrderService @Inject constructor() : OrderService {

    override suspend fun placeOrder(order: Order) {
        delay(1000)
        // Order placed, fictionally.
        return
    }
}
