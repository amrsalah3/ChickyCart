package com.narify.ecommercy.ui.ordering

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.data.OrderRepository
import com.narify.ecommercy.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderingViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //private val order =

    init {
        //placeOrder(order)
    }

    fun placeOrder(order: Order) {
        viewModelScope.launch {
            //_uiState.value = OrderingUiState.OrderLoading
            orderRepository.placeOrder(order)
            //_uiState.value = OrderingUiState.OrderPlaced
        }
    }
}
