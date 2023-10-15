package com.narify.chickycart.ui.productdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.chickycart.ChickyCartDestintationsArgs.PRODUCT_ID_ARG
import com.narify.chickycart.R
import com.narify.chickycart.data.Result
import com.narify.chickycart.data.cart.CartRepository
import com.narify.chickycart.data.products.ProductRepository
import com.narify.chickycart.model.Product
import com.narify.chickycart.ui.common.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: String = savedStateHandle[PRODUCT_ID_ARG]!!

    private val _userMessageResId: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _productResult = productRepository.getProductStream(productId)
        .map { product: Product? ->
            if (product == null) throw Exception("Cannot find the product")
            return@map Result.Success(product)
        }
        .catch<Result<Product>> { emit(Result.Error(R.string.error_loading_product_details)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    val uiState: StateFlow<ProductDetailsUiState> =
        combine(_userMessageResId, _productResult) { userMessageResId, productResult ->
            when (productResult) {
                is Result.Loading -> ProductDetailsUiState(isLoading = true)

                is Result.Success -> ProductDetailsUiState(
                    isLoading = false,
                    product = productResult.data,
                    userMessage = userMessageResId
                )

                is Result.Error -> ProductDetailsUiState(
                    isLoading = false,
                    userMessage = userMessageResId,
                    errorState = ErrorState(true, productResult.messageResId),
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductDetailsUiState(isLoading = true)
        )

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            try {
                cartRepository.addProductToCart(product)
                _userMessageResId.update { R.string.added_product_to_cart }
            } catch (e: Exception) {
                Log.e("ProductDetailsViewModel", "addProductToCart ${e.message}")
            }
        }
    }

    fun setUserMessageShown() = _userMessageResId.update { null }
}
