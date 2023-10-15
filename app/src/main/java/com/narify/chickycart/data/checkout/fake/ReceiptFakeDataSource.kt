package com.narify.chickycart.data.checkout.fake

import com.narify.chickycart.data.cart.fake.CartFakeDataSource
import com.narify.chickycart.model.Price
import com.narify.chickycart.model.ReceiptItem
import com.narify.chickycart.model.totalPrice
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
class ReceiptFakeDataSource @Inject constructor() {

    private val receiptItems = MutableStateFlow(emptyList<ReceiptItem>())

    init {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                var totalCartItemsPrice = 0.0
                CartFakeDataSource().getCartItems().forEach { totalCartItemsPrice += it.totalPrice }
                val totalCartPriceItem = ReceiptItem(
                    "Items",
                    Price(totalCartItemsPrice, totalCartItemsPrice, "USD", "$")
                )

                val totalShippingFees = 50.0
                val totalShippingFeesItem = ReceiptItem(
                    "Shipping fees",
                    Price(totalShippingFees, totalShippingFees, "USD", "$")
                )

                val totalPrice = totalCartItemsPrice + totalShippingFees
                val totalPriceItem = ReceiptItem(
                    "Total",
                    Price(totalPrice, totalPrice, "USD", "$")
                )

                receiptItems.value =
                    listOf(totalCartPriceItem, totalShippingFeesItem, totalPriceItem)
            }
        }
    }

    suspend fun getReceiptItems(): List<ReceiptItem> {
        delay(1000)

        var totalCartItemsPrice = 0.0
        CartFakeDataSource().getCartItems().forEach { totalCartItemsPrice += it.totalPrice }
        val totalCartPriceItem = ReceiptItem(
            "Items",
            Price(totalCartItemsPrice, totalCartItemsPrice, "USD", "$")
        )

        val totalShippingFees = 50.0
        val totalShippingFeesItem = ReceiptItem(
            "Shipping fees",
            Price(totalShippingFees, totalShippingFees, "USD", "$")
        )

        val totalPrice = totalCartItemsPrice + totalShippingFees
        val totalPriceItem = ReceiptItem(
            "Total",
            Price(totalPrice, totalPrice, "USD", "$")
        )

        return listOf(totalCartPriceItem, totalShippingFeesItem, totalPriceItem)
    }

    fun getReceiptItemsStream(): Flow<List<ReceiptItem>> = receiptItems

    fun getPreviewReceiptItems(): List<ReceiptItem> {
        val totalCartItemsPrice = 1000.0
        val totalCartPriceItem = ReceiptItem(
            "Items",
            Price(totalCartItemsPrice, totalCartItemsPrice, "USD", "$")
        )

        val totalShippingFees = 50.0
        val totalShippingFeesItem = ReceiptItem(
            "Shipping fees",
            Price(totalShippingFees, totalShippingFees, "USD", "$")
        )

        val totalPrice = totalCartItemsPrice + totalShippingFees
        val totalPriceItem = ReceiptItem(
            "Total",
            Price(totalPrice, totalPrice, "USD", "$")
        )

        return listOf(totalCartPriceItem, totalShippingFeesItem, totalPriceItem)
    }
}
