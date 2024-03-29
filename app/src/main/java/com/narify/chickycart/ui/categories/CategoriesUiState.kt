package com.narify.chickycart.ui.categories

import com.narify.chickycart.model.Category
import com.narify.chickycart.ui.common.ErrorState

data class CategoriesUiState(
    val isLoading: Boolean,
    val categoryItems: List<CategoryItemUiState> = emptyList(),
    val errorState: ErrorState = ErrorState()
)

data class CategoryItemUiState(
    val name: String
)

fun Category.toCategoryUiState(): CategoryItemUiState {
    return CategoryItemUiState(name)
}

fun List<Category>.toCategoriesUiState() = map(Category::toCategoryUiState)
