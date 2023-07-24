package com.narify.ecommercy.ui.categories

data class CategoriesUiState(
    val isLoading: Boolean,
    val categoryItems: List<CategoryItemUiState> = emptyList(),
    val userMessage: String? = null
)

data class CategoryItemUiState(
    val name: String
)
