package com.narify.chickycart.ui.home

import com.narify.chickycart.model.Price
import com.narify.chickycart.model.Product
import com.narify.chickycart.util.ProductsSortType

data class HomeUiState(
    val searchQuery: String? = null,
    val categoryFilterState: CategoryFilterState? = null,
    val sortUiState: SortUiState = SortUiState()
)

data class FeaturedProductItemUiState(
    val id: String,
    val imageUrl: String,
    val price: Price,
)

data class ProductItemUiState(
    val id: String,
    val name: String,
    val ratingStars: Float,
    val price: Price,
    val imageUrl: String
)

data class CategoryFilterState(val categoryName: String, val onFilterCleared: () -> Unit)

data class SortUiState(val sortType: ProductsSortType = ProductsSortType.NONE) {
    val isSortActive = (sortType !== ProductsSortType.NONE)
}

fun Product.toProductUiState() = ProductItemUiState(
    id = id,
    name = name,
    ratingStars = rating.stars,
    price = price,
    imageUrl = thumbnail
)

fun Product.toFeaturedProductUiState() = FeaturedProductItemUiState(
    id = id,
    imageUrl = thumbnail,
    price = price
)

fun List<Product>.toProductsUiState() = map(Product::toProductUiState)

fun List<Product>.toFeaturedProductsUiState() = map(Product::toFeaturedProductUiState)
