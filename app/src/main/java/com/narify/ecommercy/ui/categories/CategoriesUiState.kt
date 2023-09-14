package com.narify.ecommercy.ui.categories

import com.narify.ecommercy.ErrorState

data class CategoriesUiState(
    val isLoading: Boolean,
    val categoryItems: List<CategoryItemUiState> = emptyList(),
    val errorState: ErrorState = ErrorState()
)

data class CategoryItemUiState(
    val name: String
)
