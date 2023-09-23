package com.narify.ecommercy.ui.home

import com.narify.ecommercy.ui.common.ErrorState
import com.narify.ecommercy.util.ProductsSortType

data class HomeUiState(
    val isLoading: Boolean = false,
    val featuredProductsItems: List<FeaturedProductItemUiState> = emptyList(),
    val productItems: List<ProductItemUiState> = emptyList(),
    val categoryFilterState: CategoryFilterState? = null,
    val sortUiState: SortUiState = SortUiState(),
    val errorState: ErrorState = ErrorState()
)

data class SearchUiState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val query: String = "",
    val results: List<ProductItemUiState> = emptyList(),
    val errorState: ErrorState = ErrorState()
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

data class SortUiState(val sortType: ProductsSortType = ProductsSortType.NONE)

val SortUiState.isSortActive
    get() = (sortType !== ProductsSortType.NONE)
