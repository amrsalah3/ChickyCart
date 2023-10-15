package com.narify.chickycart.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.narify.chickycart.ChickyCartDestintationsArgs.CATEGORY_NAME_ARG
import com.narify.chickycart.data.products.ProductRepository
import com.narify.chickycart.util.ProductsSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val category: String? = savedStateHandle[CATEGORY_NAME_ARG]
    private val _searchQuery: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _categoryFilter: MutableStateFlow<String?> = MutableStateFlow(category)
    private val _sortState = MutableStateFlow(SortUiState())

    val pagingProductItems: StateFlow<PagingData<ProductItemUiState>> =
        combine(_categoryFilter, _sortState) { categoryFilter, sortState ->
            Pair(categoryFilter, sortState)
        }.flatMapLatest { (categoryFilter, sortState) ->
            productRepository.getProductsStream(
                category = categoryFilter,
                sortType = sortState.sortType
            ).map { pagingData -> pagingData.map { it.toProductUiState() } }
                .cachedIn(viewModelScope)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = PagingData.empty(PagingLoadStates.Refresh)
                )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty(PagingLoadStates.Refresh)
        )

    val pagingFeaturedProductItems: StateFlow<PagingData<FeaturedProductItemUiState>> =
        productRepository.getProductsStream(featuredProductsOnly = true)
            .map { pagingData -> pagingData.map { it.toFeaturedProductUiState() } }
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty(PagingLoadStates.Refresh)
            )

    val pagingSearchedItems: StateFlow<PagingData<ProductItemUiState>> =
        _searchQuery.flatMapLatest { query ->
            // Immediately return empty data if query string is null or blank
            if (query.isNullOrBlank()) {
                return@flatMapLatest flowOf(PagingData.empty(PagingLoadStates.Idle))
            }
            // Apply search for the products
            productRepository.getProductsStream(searchQuery = query)
                .map { pagingData -> pagingData.map { it.toProductUiState() } }
                .cachedIn(viewModelScope)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = PagingData.empty(PagingLoadStates.Refresh)
                )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty(PagingLoadStates.Refresh)
        )

    val uiState: StateFlow<HomeUiState> =
        combine(
            _searchQuery,
            _categoryFilter,
            _sortState
        ) { searchQuery, categoryFilter, sortState ->
            val categoryFilterState = categoryFilter?.let {
                CategoryFilterState(
                    categoryName = it,
                    onFilterCleared = { clearCategoryFilter() }
                )
            }
            HomeUiState(
                searchQuery = searchQuery,
                categoryFilterState = categoryFilterState,
                sortUiState = sortState
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    fun setSearching(query: String) {
        if (query.isBlank()) return
        _searchQuery.update { query }
    }

    fun setSorting(sortType: ProductsSortType) {
        _sortState.update { SortUiState(sortType) }
    }

    fun clearCategoryFilter() {
        _categoryFilter.update { null }
    }
}

private object PagingLoadStates {
    val Refresh = LoadStates(
        refresh = LoadState.Loading,
        prepend = LoadState.NotLoading(false),
        append = LoadState.NotLoading(false)
    )
    val Idle = LoadStates(
        refresh = LoadState.NotLoading(false),
        prepend = LoadState.NotLoading(false),
        append = LoadState.NotLoading(false)
    )
}
