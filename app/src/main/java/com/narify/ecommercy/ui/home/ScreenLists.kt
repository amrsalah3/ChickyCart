package com.narify.ecommercy.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.narify.ecommercy.ui.common.itemWithMaxWidth

fun LazyGridScope.itemsOfFeaturedProducts(
    featuredProductItems: LazyPagingItems<FeaturedProductItemUiState>,
    onProductClicked: (String) -> Unit
) {
    itemWithMaxWidth {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 8.dp),
        ) {
            items(featuredProductItems.itemCount) { index ->
                FeaturedProductItem(featuredProductItems[index]!!, onProductClicked)
            }

            when (featuredProductItems.loadState.append) {
                is LoadState.Loading -> item {
                    LoadingMoreProducts(Modifier.fillParentMaxHeight())
                }

                is LoadState.Error -> item {
                    ErrorLoadingMoreProducts()
                }

                else -> {}
            }
        }
    }
}

fun LazyGridScope.itemsOfProducts(
    productItems: LazyPagingItems<ProductItemUiState>,
    onProductClicked: (String) -> Unit
) {
    items(productItems.itemCount) { index ->
        ProductItem(
            productState = productItems[index]!!,
            onProductClicked = onProductClicked,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }

    when (productItems.loadState.append) {
        is LoadState.Loading -> itemWithMaxWidth {
            LoadingMoreProducts(Modifier.fillMaxWidth())
        }

        is LoadState.Error -> itemWithMaxWidth {
            ErrorLoadingMoreProducts(Modifier.fillMaxWidth())
        }

        else -> {}
    }
}
