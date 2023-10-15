package com.narify.chickycart.data.checkout.fake

import com.narify.chickycart.data.checkout.CheckoutRepository
import com.narify.chickycart.model.Order
import com.narify.chickycart.model.ReceiptItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckoutFakeRepository @Inject constructor(
    private val receiptDataSource: ReceiptFakeDataSource,
    private val orderService: OrderFakeDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CheckoutRepository {

    override fun getReceiptItemsStream(): Flow<List<ReceiptItem>> {
        return receiptDataSource.getReceiptItemsStream()
    }

    override suspend fun getReceiptItems(): List<ReceiptItem> = withContext(dispatcher) {
        return@withContext receiptDataSource.getReceiptItems()
    }

    override suspend fun placeOrder(order: Order) = withContext(dispatcher) {
        orderService.placeOrder(order)
    }
}
