package com.narify.chickycart.data.checkout

import com.narify.chickycart.data.cart.CartLocalDataSource
import com.narify.chickycart.model.Price
import com.narify.chickycart.model.ReceiptItem
import com.narify.chickycart.model.totalPrice
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptLocalDataSource @Inject constructor(private val cartDataSource: CartLocalDataSource) {

    suspend fun getReceiptItems(): List<ReceiptItem> {
        delay(1000)

        var totalCartItemsPrice = 0.0
        cartDataSource.getCartItems().forEach { totalCartItemsPrice += it.totalPrice }
        val totalCartPriceReceiptItem = ReceiptItem(
            "Items",
            Price(totalCartItemsPrice, totalCartItemsPrice, "USD", "$")
        )

        val totalShippingFees = 50.0
        val shippingReceiptItem = ReceiptItem(
            "Shipping fees",
            Price(totalShippingFees, totalShippingFees, "USD", "$")
        )

        val totalPrice = totalCartItemsPrice + totalShippingFees
        val totalPriceItem = ReceiptItem(
            "Total",
            Price(totalPrice, totalPrice, "USD", "$")
        )

        return listOf(totalCartPriceReceiptItem, shippingReceiptItem, totalPriceItem)
    }
}
