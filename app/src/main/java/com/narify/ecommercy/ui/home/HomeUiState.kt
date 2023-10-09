package com.narify.ecommercy.ui.home

import com.narify.ecommercy.util.ProductsSortType

data class HomeUiState(
    val searchQuery: String? = null,
    val categoryFilterState: CategoryFilterState? = null,
    val sortUiState: SortUiState = SortUiState()
)

data class FeaturedProductItemUiState(
    val id: String, val imageUrl: String, val priceText: String
)

data class ProductItemUiState(
    val id: String,
    val name: String,
    val ratingStars: Float,
    val priceText: String,
    val imageUrl: String
)

data class CategoryFilterState(val categoryName: String, val onFilterCleared: () -> Unit)

data class SortUiState(val sortType: ProductsSortType = ProductsSortType.NONE) {
    val isSortActive = (sortType !== ProductsSortType.NONE)
}
