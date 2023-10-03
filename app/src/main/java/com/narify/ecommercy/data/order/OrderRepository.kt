package com.narify.ecommercy.data.order

import com.narify.ecommercy.model.Order
import com.narify.ecommercy.model.ReceiptItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val receiptDataSource: ReceiptDataSource,
    private val orderService: OrderService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getReceiptItems(): List<ReceiptItem> = withContext(dispatcher) {
        return@withContext receiptDataSource.getReceiptItems()
    }

    fun getReceiptItemsStream(): Flow<List<ReceiptItem>> {
        return receiptDataSource.getReceiptItemsStream()
    }

    suspend fun placeOrder(order: Order) = withContext(dispatcher) {
        orderService.placeOrder(order)
    }
}
