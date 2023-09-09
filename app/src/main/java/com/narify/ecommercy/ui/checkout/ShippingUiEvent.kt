package com.narify.ecommercy.ui.checkout

sealed interface ShippingUiEvent {
    data class NameChanged(val inputValue: String) : ShippingUiEvent
    data class EmailChanged(val inputValue: String) : ShippingUiEvent
    data class MobileNumberChanged(val inputValue: String) : ShippingUiEvent
    data class CountryChanged(val inputValue: String) : ShippingUiEvent
    data class StateChanged(val inputValue: String) : ShippingUiEvent
    data class CityChanged(val inputValue: String) : ShippingUiEvent
    data class AddressChanged(val inputValue: String) : ShippingUiEvent
}
