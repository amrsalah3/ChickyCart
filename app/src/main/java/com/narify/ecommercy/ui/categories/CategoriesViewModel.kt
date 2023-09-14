package com.narify.ecommercy.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.ErrorState
import com.narify.ecommercy.R
import com.narify.ecommercy.data.CategoryRepository
import com.narify.ecommercy.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val uiState: StateFlow<CategoriesUiState> = categoryRepository.getCategoriesStream()
        .map { categories ->
            CategoriesUiState(
                isLoading = false,
                categoryItems = categories.toCategoriesUiState()
            )
        }
        .catch {
            emit(
                CategoriesUiState(
                    isLoading = false,
                    errorState = ErrorState(true, R.string.error_loading_categories)
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CategoriesUiState(isLoading = true)
        )
}

fun Category.toCategoryUiState(): CategoryItemUiState {
    return CategoryItemUiState(name)
}

fun List<Category>.toCategoriesUiState() = map(Category::toCategoryUiState)
