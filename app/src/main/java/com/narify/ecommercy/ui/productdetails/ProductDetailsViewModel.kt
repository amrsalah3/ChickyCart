package com.narify.ecommercy.ui.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.EcommercyDestintationsArgs.PRODUCT_ID_ARG
import com.narify.ecommercy.data.CartRepository
import com.narify.ecommercy.data.ProductRepository
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
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: String = savedStateHandle[PRODUCT_ID_ARG]!!

    val uiState: StateFlow<ProductDetailsUiState> = productRepository.getProductStream(productId)
        .map { product: Product? ->
            if (product == null) throw Exception("Cannot find the product")
            ProductDetailsUiState(
                isLoading = false,
                product = product,
            )
        }
        .catch {
            emit(
                ProductDetailsUiState(
                    isLoading = false,
                    userMessage = "Error while loading the product"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductDetailsUiState(isLoading = true)
        )

    fun addProductToCart(product: Product) {
        viewModelScope.launch { cartRepository.addProductToCart(product) }
    }
}
