package com.narify.ecommercy.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.data.CartRepository
import com.narify.ecommercy.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = cartRepository.getCartItemsStream()
        .map { cartItems ->
            CartUiState(
                isLoading = false,
                cartItems = cartItems
            )
        }
        .catch {
            emit(
                CartUiState(
                    isLoading = false,
                    userMessage = "Error while loading the cart"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState(isLoading = true)
        )

    fun increaseItemCount(product: Product) {
        viewModelScope.launch { cartRepository.addProductToCart(product) }
    }

    fun decreaseItemCount(productId: String) {
        viewModelScope.launch { cartRepository.removeProductFromCart(productId) }
    }
}
