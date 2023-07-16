package com.narify.ecommercy.ui.home

import androidx.annotation.StringRes
import com.narify.ecommercy.R

data class SearchUiState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val query: String = "",
    val results: List<ProductItemUiState> = emptyList()
)

data class FeaturedProductItemUiState(
    val imageUrl: String, val priceText: String
)

data class ProductItemUiState(
    val name: String, val ratingStars: Float, val priceText: String, val imageUrl: String
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val featuredProductsItems: List<FeaturedProductItemUiState> = emptyList(),
    val productItems: List<ProductItemUiState> = emptyList(),
    val sortUiState: SortUiState = SortUiState(),
    val userMessage: String? = null
)

data class SortUiState(@StringRes val sortTypeLabel: Int = R.string.sort_none)

val SortUiState.isSortActive
    get() = (sortTypeLabel != R.string.sort_none)

enum class ProductsSortOption {
    ALPHABETICAL, PRICE, RATING, NONE
}
