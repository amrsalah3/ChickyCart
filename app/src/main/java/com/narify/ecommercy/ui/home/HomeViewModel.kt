package com.narify.ecommercy.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.R
import com.narify.ecommercy.data.Result
import com.narify.ecommercy.data.products.ProductRepository
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.ui.common.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _sortState = MutableStateFlow(SortUiState())
    private val _productsResult = productRepository.getProductsStream()
        .map { products -> Result.Success(products) }
        .catch<Result<List<Product>>> { emit(Result.Error(R.string.error_loading_products)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    val uiState: StateFlow<HomeUiState> =
        combine(_sortState, _productsResult) { sortState, result ->
            when (result) {
                is Result.Loading -> {
                    HomeUiState(isLoading = true)
                }

                is Result.Success -> {
                    HomeUiState(
                        featuredProductsItems = result.data.toFeaturedProductsUiState(),
                        productItems = result.data.toProductsUiState(),
                        sortUiState = sortState
                    )
                }

                is Result.Error -> {
                    HomeUiState(
                        errorState = ErrorState(true, result.messageResId),
                        sortUiState = sortState
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true)
        )

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchUiState()
    )

    fun searchProducts(query: String) {
        if (query.isEmpty()) return
        
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoading = true, query = query) }
            try {
                val filteredProducts =
                    productRepository.getProducts().filter { it.name.contains(query, true) }

                _searchState.update {
                    it.copy(
                        isLoading = false,
                        results = filteredProducts.toProductsUiState()
                    )
                }
            } catch (e: Exception) {
                _searchState.update {
                    it.copy(
                        isLoading = false,
                        errorState = ErrorState(true, R.string.error_finding_product)
                    )
                }
            }
        }
    }

    fun setSortType(@StringRes sortTypeLabel: Int) {
        _sortState.update { it.copy(sortTypeLabel = sortTypeLabel) }
    }
}


fun Product.toProductUiState() = ProductItemUiState(
    id = id,
    name = name,
    ratingStars = rating.stars,
    priceText = price.raw,
    imageUrl = getThumbnail()
)

fun Product.toFeaturedProductUiState() = FeaturedProductItemUiState(
    id = id,
    imageUrl = getThumbnail(),
    priceText = price.raw
)

fun List<Product>.toProductsUiState() = map(Product::toProductUiState)

fun List<Product>.toFeaturedProductsUiState() = map(Product::toFeaturedProductUiState)
