package com.narify.chickycart.data.checkout

import com.narify.chickycart.model.Order
import com.narify.chickycart.model.ReceiptItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface CheckoutRepository {
    fun getReceiptItemsStream(): Flow<List<ReceiptItem>>
    suspend fun getReceiptItems(): List<ReceiptItem>
    suspend fun placeOrder(order: Order)
}
