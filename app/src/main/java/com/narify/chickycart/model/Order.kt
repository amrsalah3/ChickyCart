package com.narify.chickycart.model

data class Order(
    val items: List<OrderItem>,
    val shipping: ShippingDetails
)

data class OrderItem(
    val productId: String,
    val count: Int,
)

data class ShippingDetails(
    val name: String,
    val mobileNumber: String,
    val email: String,
    val country: String,
    val state: String,
    val city: String,
    val address: String
)
