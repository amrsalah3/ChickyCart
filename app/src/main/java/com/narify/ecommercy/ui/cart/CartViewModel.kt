package com.narify.ecommercy.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.ErrorState
import com.narify.ecommercy.R
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
                    errorState = ErrorState(true, R.string.error_loading_cart)
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState(isLoading = true)
        )

    fun increaseItemCount(product: Product) {
        viewModelScope.launch {
            try {
                cartRepository.addProductToCart(product)
            } catch (e: Exception) {
                Log.e("CartViewModel", "increaseItemCount: ${e.message}")
            }
        }
    }

    fun decreaseItemCount(productId: String) {
        viewModelScope.launch {
            try {
                cartRepository.removeProductFromCart(productId)
            } catch (e: Exception) {
                Log.e("CartViewModel", "increaseItemCount: ${e.message}")
            }
        }
    }
}
