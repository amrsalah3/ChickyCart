package com.narify.chickycart.data.checkout

import com.narify.chickycart.data.checkout.fake.OrderFakeDataSource
import com.narify.chickycart.model.Order
import com.narify.chickycart.model.ReceiptItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckoutDefaultRepository @Inject constructor(
    private val receiptDataSource: ReceiptLocalDataSource,
    private val orderService: OrderFakeDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CheckoutRepository {

    override fun getReceiptItemsStream(): Flow<List<ReceiptItem>> = flow {
        emit(getReceiptItems())
    }.flowOn(dispatcher)

    override suspend fun getReceiptItems(): List<ReceiptItem> = withContext(dispatcher) {
        return@withContext receiptDataSource.getReceiptItems()
    }

    override suspend fun placeOrder(order: Order) = withContext(dispatcher) {
        orderService.placeOrder(order)
    }
}
