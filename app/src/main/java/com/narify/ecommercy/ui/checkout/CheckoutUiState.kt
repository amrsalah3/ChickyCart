package com.narify.ecommercy.ui.checkout

import com.narify.ecommercy.ErrorState

data class CheckoutUiState(
    val isLoading: Boolean = false,
    val shippingInputState: ShippingInputState = ShippingInputState(),
    val shippingErrorState: ShippingErrorState = ShippingErrorState(),
    val receiptItems: List<ReceiptUiItem> = emptyList(),
    val ordering: OrderingUiState? = null,
    val userMessage: String? = null,
)

data class ShippingInputState(
    val name: String = "",
    val email: String = "",
    val mobileNumber: String = "",
    val country: String = "",
    val state: String = "",
    val city: String = "",
    val address: String = ""
)

data class ShippingErrorState(
    val name: ErrorState = ErrorState(),
    val email: ErrorState = ErrorState(),
    val mobileNumber: ErrorState = ErrorState(),
    val country: ErrorState = ErrorState(),
    val state: ErrorState = ErrorState(),
    val city: ErrorState = ErrorState(),
    val address: ErrorState = ErrorState()
)

data class ReceiptUiItem(val name: String, val price: String)

sealed interface OrderingUiState {
    object OrderLoading : OrderingUiState
    object OrderPlaced : OrderingUiState
    data class OrderFailed(val errorMessage: String) : OrderingUiState
}
