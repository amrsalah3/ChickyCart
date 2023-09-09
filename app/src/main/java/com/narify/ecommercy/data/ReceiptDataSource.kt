package com.narify.ecommercy.data

import com.narify.ecommercy.model.Price
import com.narify.ecommercy.model.ReceiptItem
import com.narify.ecommercy.model.totalPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface ReceiptDataSource {
    suspend fun getReceiptItems(): List<ReceiptItem>
    fun getReceiptItemsStream(): Flow<List<ReceiptItem>>
}

@Singleton
class FakeReceiptDataSource @Inject constructor() : ReceiptDataSource {

    private val receiptItems = MutableStateFlow(emptyList<ReceiptItem>())

    init {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                var totalItemsPrice = 0.0
                FakeCartDataSource().getCartItems().forEach { totalItemsPrice += it.totalPrice }
                val totalCartPriceReceiptItem = ReceiptItem(
                    "Items",
                    Price(
                        totalItemsPrice,
                        totalItemsPrice,
                        "EGP",
                        "£",
                        raw = "EGP $totalItemsPrice"
                    )
                )

                val totalShippingFees = 50.0
                val shippingReceiptItem = ReceiptItem(
                    "Shipping fees",
                    Price(totalShippingFees, totalShippingFees, "EGP", "£", raw = "EGP 50")
                )

                receiptItems.value = listOf(totalCartPriceReceiptItem, shippingReceiptItem)
            }
        }
    }

    override suspend fun getReceiptItems(): List<ReceiptItem> {
        delay(1000)

        var totalCartItemsPrice = 0.0
        FakeCartDataSource().getCartItems().forEach { totalCartItemsPrice += it.totalPrice }
        val totalCartPriceReceiptItem = ReceiptItem(
            "Items",
            Price(totalCartItemsPrice, totalCartItemsPrice, "EGP", "£", raw = "EGP 50")
        )

        val totalShippingFees = 50.0
        val shippingReceiptItem = ReceiptItem(
            "Shipping fees",
            Price(totalShippingFees, totalShippingFees, "EGP", "£", raw = "EGP 50")
        )

        return listOf(totalCartPriceReceiptItem, shippingReceiptItem)
    }

    override fun getReceiptItemsStream(): Flow<List<ReceiptItem>> = receiptItems
}
