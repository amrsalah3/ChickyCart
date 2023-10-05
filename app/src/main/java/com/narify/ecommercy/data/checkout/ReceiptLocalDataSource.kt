package com.narify.ecommercy.data.checkout

import com.narify.ecommercy.data.cart.CartLocalDataSource
import com.narify.ecommercy.model.Price
import com.narify.ecommercy.model.ReceiptItem
import com.narify.ecommercy.model.totalPrice
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
            Price(totalPrice, totalPrice, "USD", "$", raw = "$$totalPrice")
        )

        return listOf(totalCartPriceReceiptItem, shippingReceiptItem, totalPriceItem)
    }
}
