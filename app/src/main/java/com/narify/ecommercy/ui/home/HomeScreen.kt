package com.narify.ecommercy.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.ecommercy.R
import com.narify.ecommercy.data.products.fake.ProductFakeDataSource
import com.narify.ecommercy.ui.EmptyContent
import com.narify.ecommercy.ui.common.DevicePreviews
import com.narify.ecommercy.ui.theme.EcommercyThemePreview
import com.narify.ecommercy.util.ProductsSortType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    onProductClicked: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    fun showBottomSheet() {
        coroutineScope.launch {
            bottomSheetState.show()
        }
    }

    fun collapseBottomSheet() {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    if (uiState.isLoading) LoadingProductsList()
    else if (uiState.errorState.hasError) EmptyContent(uiState.errorState.errorMsgResId)
    else HomeScreen(
        featuredProducts = uiState.featuredProductsItems,
        allProducts = uiState.productItems,
        searchUiState = searchState,
        bottomSheetState = bottomSheetState,
        onSearchRequested = viewModel::searchProducts,
        onSortIconClicked = { showBottomSheet() },
        onSortApplied = { sortType ->
            viewModel.setSorting(sortType)
            collapseBottomSheet()
        },
        appliedSortType = uiState.sortUiState.sortType,
        onDismissBottomSheet = { collapseBottomSheet() },
        onProductClicked = onProductClicked,
        categoryFilter = uiState.categoryFilterState,
        sortIconBackgroundColor = if (uiState.sortUiState.isSortActive) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    featuredProducts: List<FeaturedProductItemUiState>,
    allProducts: List<ProductItemUiState>,
    searchUiState: SearchUiState,
    bottomSheetState: SheetState,
    onSearchRequested: (String) -> Unit,
    onSortIconClicked: () -> Unit,
    onSortApplied: (ProductsSortType) -> Unit,
    appliedSortType: ProductsSortType,
    onDismissBottomSheet: () -> Unit,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    categoryFilter: CategoryFilterState? = null,
    sortIconBackgroundColor: Color = Color.Transparent,
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Top search bar
        HomeSearchBar(
            searchUiState = searchUiState,
            onSearchRequested = onSearchRequested,
            onSortIconClicked = onSortIconClicked,
            sortIconBackgroundColor = sortIconBackgroundColor,
            onProductClicked = onProductClicked
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            // Featured products section
            if (allProducts.isNotEmpty() && featuredProducts.isNotEmpty() && categoryFilter == null) {
                item {
                    SectionLabel(R.string.section_featured_products)
                    FeaturedProductsRow(
                        products = featuredProducts,
                        onProductClicked = onProductClicked
                    )
                }
            }

            // All (or filtered) products section
            if (allProducts.isNotEmpty()) {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        if (categoryFilter == null) SectionLabel(R.string.section_all_products)
                        else CategoryFilterChip(categoryFilter)
                    }
                }

                items(allProducts) { productItem ->
                    Box(Modifier.padding(horizontal = 8.dp)) {
                        ProductItem(
                            productState = productItem,
                            onProductClicked = onProductClicked
                        )
                    }
                }
            }

        }

        // No products state
        if (allProducts.isEmpty()) {
            if (categoryFilter != null) CategoryFilterChip(categoryFilter)
            EmptyContent(R.string.empty_products)
        }

        // Bottom sheet for showing sort options
        SortOptionsBottomSheet(
            sheetState = bottomSheetState,
            onSortApplied = onSortApplied,
            appliedSortType = appliedSortType,
            onDismiss = onDismissBottomSheet
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoryFilterChip(
    categoryFilter: CategoryFilterState,
    modifier: Modifier = Modifier
) {
    ElevatedFilterChip(
        selected = true,
        onClick = { /* Do nothing */ },
        label = { Text(categoryFilter.categoryName) },
        trailingIcon = {
            val iconSize = 20.dp
            IconButton(
                onClick = categoryFilter.onFilterCleared,
                modifier = modifier.size(iconSize)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.content_description_clear_filter),
                    modifier = modifier.size(iconSize)
                )
            }
        },
        modifier = modifier.padding(horizontal = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortOptionsBottomSheet(
    sheetState: SheetState,
    onSortApplied: (ProductsSortType) -> Unit,
    appliedSortType: ProductsSortType,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    /*
    * selectedOption is the radio button selected as the sheet is appearing on the screen
    * This variable is reset to the already (appliedSortType) when dismissing the sheet (not applying).
    */

    var selectedOption by rememberSaveable { mutableStateOf(appliedSortType) }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                selectedOption = appliedSortType
                onDismiss()
            },
            modifier = modifier
        ) {
            SortOptionsRadioGroup(
                sortOptions = ProductsSortType.values(),
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            Button(
                onClick = { onSortApplied(selectedOption) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(stringResource(R.string.apply))
            }

            Spacer(Modifier.height(58.dp))
        }
    }
}

@Composable
fun SortOptionsRadioGroup(
    modifier: Modifier = Modifier,
    sortOptions: Array<ProductsSortType>,
    selectedOption: ProductsSortType,
    onOptionSelected: (ProductsSortType) -> Unit
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Text(
            stringResource(R.string.sort_by),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        sortOptions.forEach { option ->
            val labelResId = option.labelResId
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.selectable(
                    selected = (labelResId == selectedOption.labelResId),
                    onClick = { onOptionSelected(option) }
                )
            ) {
                RadioButton(
                    selected = (labelResId == selectedOption.labelResId),
                    onClick = { onOptionSelected(option) }
                )
                Text(stringResource(labelResId), modifier.padding(end = 12.dp))
            }
        }
    }
}

@Composable
fun FeaturedProductItem(
    productState: FeaturedProductItemUiState,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 6.dp,
        modifier = modifier.size(120.dp)
    ) {
        Box(Modifier.clickable(true) { onProductClicked(productState.id) }) {
            AsyncImage(
                model = productState.imageUrl,
                placeholder = painterResource(R.drawable.sample_product_item),
                contentDescription = stringResource(R.string.content_description_product_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = productState.priceText,
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(color = Color.Red)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProductItem(
    productState: ProductItemUiState,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = cardColor,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier
                .height(140.dp)
                .fillMaxWidth()
                .clickable(true) { onProductClicked(productState.id) }
        ) {
            AsyncImage(
                model = productState.imageUrl,
                placeholder = painterResource(R.drawable.sample_product_item),
                contentDescription = stringResource(R.string.content_description_product_image),
                contentScale = ContentScale.Crop,
                modifier = modifier.weight(1.2f)
            )
            Column(
                Modifier
                    .padding(16.dp)
                    .weight(2f)
            ) {
                Text(
                    text = productState.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                RatingBar(
                    value = productState.ratingStars,
                    config = RatingBarConfig()
                        .activeColor(MaterialTheme.colorScheme.primary)
                        .inactiveColor(MaterialTheme.colorScheme.inversePrimary)
                        .size(20.dp),
                    onValueChange = {},
                    onRatingChanged = { onProductClicked(productState.id) },
                    modifier = Modifier.weight(0.5f)
                )
                Text(text = productState.priceText, modifier = Modifier.weight(0.5f))
            }
        }
    }
}

@Composable
fun FeaturedProductsRow(
    products: List<FeaturedProductItemUiState>,
    onProductClicked: (String) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(products) { productItem ->
            FeaturedProductItem(productItem, onProductClicked)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(
    searchUiState: SearchUiState,
    onSearchRequested: (String) -> Unit,
    onSortIconClicked: () -> Unit,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    sortIconBackgroundColor: Color = Color.Transparent,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { onSearchRequested(query) },
        active = active,
        onActiveChange = {
            query = ""
            active = it
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.content_description_close_icon),
                    modifier = Modifier.clickable(true) { query = "" }
                )
            } else {
                IconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = sortIconBackgroundColor
                    ),
                    onClick = onSortIconClicked
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_filter),
                        contentDescription = stringResource(R.string.content_description_sort_icon)
                    )
                }
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.searchbar_placeholder))
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (searchUiState.isLoading) LoadingProductsList()
        else if (searchUiState.errorState.hasError) EmptyContent(searchUiState.errorState.errorMsgResId)
        else if (searchUiState.query.isNotEmpty() && searchUiState.results.isEmpty()) {
            EmptyContent(R.string.empty_products)
        } else {
            SearchProductsContent(
                products = searchUiState.results,
                onProductClicked = onProductClicked
            )
        }
    }
}

@Composable
fun SearchProductsContent(
    products: List<ProductItemUiState>,
    onProductClicked: (String) -> Unit,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(products) { productItem ->
            ProductItem(
                productState = productItem,
                onProductClicked = onProductClicked,
                cardColor = cardColor
            )
        }
    }
}

@Composable
fun SectionLabel(@StringRes labelResId: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(labelResId).uppercase(),
        style = MaterialTheme.typography.titleMedium,
        color = Color.Gray,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@DevicePreviews
@Composable
fun HomeScreenPreview(
    @PreviewParameter(CategoryFilterPreviewParameterProvider::class) filter: CategoryFilterState?
) {
    EcommercyThemePreview {
        ProductFakeDataSource().getPreviewProducts().let {
            val featuredProducts = it.toFeaturedProductsUiState()
            val allProducts = it.toProductsUiState()
            HomeScreen(
                featuredProducts = featuredProducts,
                allProducts = allProducts,
                searchUiState = SearchUiState(),
                onSearchRequested = { },
                onSortIconClicked = { },
                onSortApplied = { },
                appliedSortType = ProductsSortType.NONE,
                onProductClicked = { },
                categoryFilter = filter,
                bottomSheetState = rememberModalBottomSheetState(),
                onDismissBottomSheet = { }
            )
        }
    }
}

@Preview
@Composable
fun FeaturedProductItemPreview() {
    EcommercyThemePreview {
        FeaturedProductItem(
            FeaturedProductItemUiState(
                id = "p0",
                imageUrl = "https://m.media-amazon.com/images/I/713xpOG8zZL._AC_SL1500_.jpg",
                priceText = "100 EGP"
            ),
            onProductClicked = { }
        )
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    EcommercyThemePreview {
        ProductItem(
            productState = ProductItemUiState(
                id = "p0",
                name = "Camera",
                ratingStars = 4.5f,
                priceText = "500 EGP",
                imageUrl = "https://m.media-amazon.com/images/I/713xpOG8zZL._AC_SL1500_.jpg",
            ),
            onProductClicked = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = "spec:width=1080px,height=1080px")
@Preview(device = "spec:width=1080px,height=1080px", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SortOptionsBottomSheetPreview() {
    EcommercyThemePreview {
        SortOptionsBottomSheet(
            onSortApplied = { },
            sheetState = SheetState(
                initialValue = SheetValue.Expanded,
                skipPartiallyExpanded = true
            ),
            appliedSortType = ProductsSortType.NONE,
            onDismiss = { }
        )
    }
}
