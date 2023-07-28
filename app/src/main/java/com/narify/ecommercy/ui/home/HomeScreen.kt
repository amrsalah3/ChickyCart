package com.narify.ecommercy.ui.home

import android.content.res.Configuration
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.ecommercy.R
import com.narify.ecommercy.data.FakeProductsDataSource
import com.narify.ecommercy.ui.LoadingContent
import com.narify.ecommercy.ui.theme.EcommercyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    if (uiState.isLoading) LoadingContent()
    else HomeScreen(
        featuredProducts = uiState.featuredProductsItems,
        allProducts = uiState.productItems,
        searchUiState = searchState,
        onSearchRequested = { query -> viewModel.searchProducts(query) },
        onSortApplied = { typeResId -> viewModel.setSortType(typeResId) },
        onProductClicked = onProductClicked,
        sortIconBackgroundColor = if (uiState.sortUiState.isSortActive) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    featuredProducts: List<FeaturedProductItemUiState>,
    allProducts: List<ProductItemUiState>,
    searchUiState: SearchUiState,
    onSearchRequested: (String) -> Unit,
    onSortApplied: (Int) -> Unit,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    sortIconBackgroundColor: Color = Color.Transparent,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val coroutineScope = rememberCoroutineScope()

        HomeSearchBar(
            searchUiState = searchUiState,
            onSearchRequested = onSearchRequested,
            onSortIconClicked = {
                coroutineScope.launch {
                    sheetState.show()
                }
            },
            sortIconBackgroundColor = sortIconBackgroundColor,
            onProductClicked = onProductClicked
        )
        HomeSection(title = "Featured products") {
            FeaturedProductsRow(products = featuredProducts, onProductClicked = onProductClicked)
        }
        HomeSection(title = "All products") {
            AllProductsColumn(products = allProducts, onProductClicked = onProductClicked)
        }
        SortOptionsBottomSheet(
            sheetState = sheetState, onSortApplied = onSortApplied
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortOptionsBottomSheet(
    onSortApplied: (Int) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    fun collapseSheet() = coroutineScope.launch {
        sheetState.hide()
    }

    val sortOptions = intArrayOf(
        R.string.sort_alphabetical, R.string.sort_price, R.string.sort_rating, R.string.sort_none
    )
    var selectedOption by rememberSaveable { mutableIntStateOf(sortOptions[3]) }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState, onDismissRequest = { collapseSheet() }, modifier = modifier
        ) {
            SortOptionsRadioGroup(sortOptions = sortOptions,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it })
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
                onClick = {
                    collapseSheet()
                    onSortApplied(selectedOption)
                }) {
                Text("Apply")
            }
            Spacer(Modifier.height(58.dp))
        }
    }
}

@Composable
fun SortOptionsRadioGroup(
    modifier: Modifier = Modifier,
    @StringRes sortOptions: IntArray,
    @StringRes selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Text("Sort by", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        sortOptions.forEach { option ->
            Row(
                modifier.selectable(selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) })
                Text(stringResource(option), modifier.padding(end = 12.dp))
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
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
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
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    productState: ProductItemUiState
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = cardColor,
        shadowElevation = 6.dp,
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
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier.weight(1.2f)
            )
            Column(
                modifier
                    .padding(16.dp)
                    .weight(2f)
            ) {
                Text(
                    text = productState.name, maxLines = 2, overflow = TextOverflow.Ellipsis
                )
                RatingBar(value = productState.ratingStars,
                    config = RatingBarConfig().activeColor(MaterialTheme.colorScheme.primary)
                        .size(20.dp),
                    onValueChange = {},
                    onRatingChanged = {})
                Text(productState.priceText)
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
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(products) { productItem ->
            FeaturedProductItem(productItem, onProductClicked)
        }
    }
}

@Composable
fun AllProductsColumn(
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
                cardColor = cardColor,
                onProductClicked = onProductClicked
            )
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
    val backgroundColor = if (active) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { onSearchRequested(query) },
        active = active,
        onActiveChange = {
            query = ""
            active = it
        },
        colors = SearchBarDefaults.colors(
            containerColor = backgroundColor, inputFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = backgroundColor, unfocusedContainerColor = backgroundColor
            )
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (active) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Close icon",
                    modifier = Modifier.clickable(true) { query = "" })
            } else {
                IconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = sortIconBackgroundColor
                    ), onClick = onSortIconClicked
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_filter),
                        contentDescription = "Sort or filter icon"
                    )
                }
            }
        },
        placeholder = {
            Text(text = "Search")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (searchUiState.isLoading) LoadingContent()
        else AllProductsColumn(
            products = searchUiState.results,
            onProductClicked = onProductClicked
        )
    }
}

@Composable
fun HomeSection(title: String, content: @Composable () -> Unit) {
    Text(
        text = title.uppercase(),
        fontFamily = FontFamily.Default,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(8.dp)
    )
    content()
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    EcommercyTheme {
        FakeProductsDataSource().getPreviewProducts().let {
            val featuredProducts = it.toFeaturedProductsUiState()
            val allProducts = it.toProductsUiState()
            HomeScreen(
                featuredProducts = featuredProducts,
                allProducts = allProducts,
                searchUiState = SearchUiState(),
                onSearchRequested = {},
                onSortApplied = {},
                onProductClicked = {}
            )
        }
    }
}

@Preview(device = "id:pixel_2", showBackground = true)
@Composable
fun FeaturedProductItemPreview() {
    EcommercyTheme {
        FeaturedProductItem(
            FeaturedProductItemUiState(
                id = "p0",
                imageUrl = "https://m.media-amazon.com/images/I/713xpOG8zZL._AC_SL1500_.jpg",
                priceText = "100 EGP"
            ),
            onProductClicked = {}
        )
    }
}

@Preview(device = "id:pixel_2", showBackground = true)
@Composable
fun ProductItemPreview() {
    EcommercyTheme {
        ProductItem(
            productState = ProductItemUiState(
                id = "p0",
                name = "Camera",
                ratingStars = 4.5f,
                priceText = "500 EGP",
                imageUrl = "https://m.media-amazon.com/images/I/713xpOG8zZL._AC_SL1500_.jpg",
            ),
            onProductClicked = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = "spec:width=1080px,height=1080px", showBackground = true)
@Composable
fun SortOptionsBottomSheetPreview() {
    EcommercyTheme {
        SortOptionsBottomSheet(
            onSortApplied = {}, sheetState = SheetState(
                initialValue = SheetValue.Expanded, skipPartiallyExpanded = true
            )
        )
    }
}
