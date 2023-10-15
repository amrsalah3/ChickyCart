package com.narify.chickycart.ui.checkout

import androidx.annotation.StringRes
import com.narify.chickycart.model.ReceiptItem
import com.narify.chickycart.ui.common.ErrorState

data class CheckoutUiState(
    val isLoading: Boolean = false,
    val shippingInputState: ShippingInputState = ShippingInputState(),
    val shippingErrorState: ShippingErrorState = ShippingErrorState(),
    val receiptItemsState: List<ReceiptUiItemState> = emptyList(),
    val ordering: OrderingUiState? = null,
    val shouldScrollToShowError: Boolean = false,
    val userMessage: String? = null,
    val errorState: ErrorState = ErrorState(),
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

data class ReceiptUiItemState(val name: String, val price: String)

sealed interface OrderingUiState {
    object OrderLoading : OrderingUiState
    object OrderPlaced : OrderingUiState
    data class OrderFailed(@StringRes val messageResId: Int) : OrderingUiState
}

fun ReceiptItem.toReceiptUiItemState() = ReceiptUiItemState(name, price.raw)
fun List<ReceiptItem>.toReceiptUiItemsState() = map(ReceiptItem::toReceiptUiItemState)
