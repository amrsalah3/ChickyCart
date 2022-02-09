package com.narify.ecommerce.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommerce.model.CartItem
import com.narify.ecommerce.model.Product
import com.narify.ecommerce.model.Result.Error
import com.narify.ecommerce.model.Result.Success
import com.narify.ecommerce.usecase.AddCartItemUseCase
import com.narify.ecommerce.usecase.GetCartItemsUseCase
import com.narify.ecommerce.usecase.RemoveCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItems: GetCartItemsUseCase,
    private val addCartItem: AddCartItemUseCase,
    private val removeCartItem: RemoveCartItemUseCase
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    init {
        fetchCartItems()
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            addCartItem(product)
            fetchCartItems()
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            removeCartItem(product)
            fetchCartItems()
        }
    }

    fun fetchCartItems() {
        viewModelScope.launch {
            _loading.value = true

            getCartItems().let {
                when (it) {
                    is Success -> setContentState(it.data)
                    is Error -> setErrorState(it.message)
                }
            }
        }
    }

    private fun setContentState(items: List<CartItem>) {
        _cartItems.value = items
        _error.value = false
        _loading.value = false
    }

    private fun setErrorState(message: String) {
        _error.value = true
        _loading.value = false
        // TODO: Analyze error message
    }

}