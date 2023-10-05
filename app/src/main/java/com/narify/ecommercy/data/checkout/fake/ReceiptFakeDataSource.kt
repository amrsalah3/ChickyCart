package com.narify.ecommercy.data.checkout.fake

import com.narify.ecommercy.data.cart.fake.CartFakeDataSource
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
class ReceiptFakeDataSource @Inject constructor() {

    private val receiptItems = MutableStateFlow(emptyList<ReceiptItem>())

    init {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                var totalCartItemsPrice = 0.0
                CartFakeDataSource().getCartItems().forEach { totalCartItemsPrice += it.totalPrice }
                val totalCartPriceReceiptItem = ReceiptItem(
                    "Items",
                    Price(
                        totalCartItemsPrice,
                        totalCartItemsPrice,
                        "USD",
                        "$",
                        raw = "$$totalCartItemsPrice"
                    )
                )

                val totalShippingFees = 50.0
                val shippingReceiptItem = ReceiptItem(
                    "Shipping fees",
                    Price(
                        totalShippingFees,
                        totalShippingFees,
                        "USD",
                        "$",
                        raw = "$$totalShippingFees"
                    )
                )

                val totalPrice = totalCartItemsPrice + totalShippingFees
                val totalPriceItem = ReceiptItem(
                    "Total",
                    Price(totalPrice, totalPrice, "USD", "$", raw = "$$totalPrice")
                )

                receiptItems.value =
                    listOf(totalCartPriceReceiptItem, shippingReceiptItem, totalPriceItem)
            }
        }
    }

    suspend fun getReceiptItems(): List<ReceiptItem> {
        delay(1000)

        var totalCartItemsPrice = 0.0
        CartFakeDataSource().getCartItems().forEach { totalCartItemsPrice += it.totalPrice }
        val totalCartPriceReceiptItem = ReceiptItem(
            "Items",
            Price(
                totalCartItemsPrice,
                totalCartItemsPrice,
                "$",
                "£",
                raw = "$ $totalCartItemsPrice"
            )
        )

        val totalShippingFees = 50.0
        val shippingReceiptItem = ReceiptItem(
            "Shipping fees",
            Price(totalShippingFees, totalShippingFees, "USD", "$", raw = "$$totalShippingFees")
        )

        val totalPrice = totalCartItemsPrice + totalShippingFees
        val totalPriceItem = ReceiptItem(
            "Total",
            Price(totalPrice, totalPrice, "USD", "$", raw = "$$totalPrice")
        )

        return listOf(totalCartPriceReceiptItem, shippingReceiptItem, totalPriceItem)
    }

    fun getReceiptItemsStream(): Flow<List<ReceiptItem>> = receiptItems

    fun getPreviewReceiptItems(): List<ReceiptItem> {
        val totalCartItemsPrice = 1000.0
        val totalCartPriceReceiptItem = ReceiptItem(
            "Items",
            Price(
                totalCartItemsPrice,
                totalCartItemsPrice,
                "USD",
                "$",
                raw = "$$totalCartItemsPrice"
            )
        )

        val totalShippingFees = 50.0
        val shippingReceiptItem = ReceiptItem(
            "Shipping fees",
            Price(totalShippingFees, totalShippingFees, "USD", "$", raw = "$$totalShippingFees")
        )

        val totalPrice = totalCartItemsPrice + totalShippingFees
        val totalPriceItem = ReceiptItem(
            "Total",
            Price(totalPrice, totalPrice, "$", "$", raw = "$$totalPrice")
        )

        return listOf(totalCartPriceReceiptItem, shippingReceiptItem, totalPriceItem)
    }
}
