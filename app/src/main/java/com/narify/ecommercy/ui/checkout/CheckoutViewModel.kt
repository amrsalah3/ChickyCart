package com.narify.ecommercy.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.ErrorState
import com.narify.ecommercy.data.CartRepository
import com.narify.ecommercy.data.OrderRepository
import com.narify.ecommercy.model.Order
import com.narify.ecommercy.model.OrderItem
import com.narify.ecommercy.model.ReceiptItem
import com.narify.ecommercy.model.ShippingDetails
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidAddress
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidCity
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidCountry
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidEmail
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidMobileNumber
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidName
import com.narify.ecommercy.util.ShippingFieldsValidator.isValidState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateReceiptItemsState()
    }

    private fun updateReceiptItemsState() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val receiptUiItems = orderRepository.getReceiptItems().toReceiptUiItems()

            _uiState.update {
                it.copy(isLoading = false, receiptItems = receiptUiItems)
            }
        }
    }

    fun onShippingUiEvent(event: ShippingUiEvent) {
        when (event) {
            is ShippingUiEvent.NameChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(name = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(name = ErrorState())
                    )
                }
            }

            is ShippingUiEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(email = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(email = ErrorState())
                    )
                }
            }

            is ShippingUiEvent.MobileNumberChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(mobileNumber = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(mobileNumber = ErrorState())
                    )
                }
            }

            is ShippingUiEvent.CountryChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(country = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(country = ErrorState())
                    )
                }
            }

            is ShippingUiEvent.StateChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(state = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(state = ErrorState())
                    )
                }
            }

            is ShippingUiEvent.CityChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(city = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(city = ErrorState())
                    )
                }
            }

            is ShippingUiEvent.AddressChanged -> {
                _uiState.update {
                    it.copy(
                        shippingInputState = it.shippingInputState.copy(address = event.inputValue),
                        shippingErrorState = it.shippingErrorState.copy(address = ErrorState())
                    )
                }
            }
        }
    }

    fun placeOrder() {
        viewModelScope.launch {
            // Ensure that all fields are valid before proceeding
            if (!validateAllFields()) return@launch

            // Proceed to place the order
            _uiState.update {
                it.copy(ordering = OrderingUiState.OrderLoading)
            }

            with(_uiState.value.shippingInputState) {
                val orderItems = mutableListOf<OrderItem>()
                cartRepository.getCartItems().forEach {
                    orderItems += OrderItem(it.product.id, it.count)
                }

                val shippingDetails = ShippingDetails(
                    name = name,
                    mobileNumber = mobileNumber,
                    email = email,
                    country = country,
                    state = state,
                    city = city,
                    address = address
                )

                val order = Order(orderItems, shippingDetails)
                orderRepository.placeOrder(order)
            }

            _uiState.update {
                it.copy(ordering = OrderingUiState.OrderPlaced)
            }
        }
    }

    /**
     * Validates all input fields related to shipping information and updates the UI state accordingly.
     *
     * This function checks the validity of each shipping-related input field (name, email, mobile number,
     * country, state, city, and address) and updates the error state for each field in the UI state.
     *
     * @return `true` if all input fields are valid; `false` if any of the input fields have errors.
     */
    private fun validateAllFields(): Boolean {
        // Update the error state for each shipping-related input field in the UI state.
        with(_uiState.value.shippingInputState) {
            _uiState.update {
                it.copy(
                    shippingErrorState = it.shippingErrorState.copy(
                        name = ErrorState(!isValidName(name)),
                        email = ErrorState(!isValidEmail(email)),
                        mobileNumber = ErrorState(!isValidMobileNumber(mobileNumber)),
                        country = ErrorState(!isValidCountry(country)),
                        state = ErrorState(!isValidState(state)),
                        city = ErrorState(!isValidCity(city)),
                        address = ErrorState(!isValidAddress(address)),
                    )
                )
            }
        }

        // Check if any of the input fields have errors, show a ui message accordingly and return.
        with(_uiState.value.shippingErrorState) {
            if (name.hasError || email.hasError || mobileNumber.hasError || country.hasError ||
                state.hasError || city.hasError || address.hasError
            ) {
                _uiState.update {
                    it.copy(userMessage = "One or more fields are empty or have invalid input")
                }
                return false
            } else {
                return true
            }
        }
    }
}

fun ReceiptItem.toReceiptUiItem() = ReceiptUiItem(name, price.raw)
fun List<ReceiptItem>.toReceiptUiItems() = map(ReceiptItem::toReceiptUiItem)
