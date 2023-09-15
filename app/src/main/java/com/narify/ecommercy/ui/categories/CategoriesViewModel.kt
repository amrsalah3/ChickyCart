package com.narify.ecommercy.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narify.ecommercy.R
import com.narify.ecommercy.data.categories.CategoriesRepository
import com.narify.ecommercy.model.Category
import com.narify.ecommercy.ui.common.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    val uiState: StateFlow<CategoriesUiState> = categoriesRepository.getCategoriesStream()
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
