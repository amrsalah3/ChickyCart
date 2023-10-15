package com.narify.chickycart.ui.productdetails

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.chickycart.R
import com.narify.chickycart.data.products.fake.ProductFakeDataSource
import com.narify.chickycart.model.Product
import com.narify.chickycart.ui.EmptyContent
import com.narify.chickycart.ui.common.DevicePreviews
import com.narify.chickycart.ui.common.ProductAsyncImage
import com.narify.chickycart.ui.theme.ChickyCartThemePreview
import com.narify.chickycart.ui.theme.DarkGreen

@Composable
fun ProductDetailsRoute(
    onCartClicked: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = SnackbarHostState()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        if (uiState.isLoading) LoadingProductDetails()
        else if (uiState.errorState.hasError) EmptyContent(uiState.errorState.errorMsgResId)
        else ProductDetailsScreen(
            product = uiState.product!!,
            onAddToCartClicked = viewModel::addProductToCart,
            onCartClicked = onCartClicked,
            modifier = Modifier.padding(paddingValues)
        )

        uiState.userMessage?.let {
            val message = stringResource(it)
            val action = stringResource(R.string.action_dismiss)
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = SnackbarDuration.Short
                )
                viewModel.setUserMessageShown()
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(
    product: Product,
    onAddToCartClicked: (Product) -> Unit,
    onCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        if (maxWidth < 450.dp) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                ImagesSection(
                    images = product.images,
                    pagerModifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F)
                )
                DetailsSection(
                    product = product,
                    onAddToCartClicked = onAddToCartClicked,
                    onCartClicked = onCartClicked,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
        } else {
            Row(Modifier.padding(16.dp)) {
                ImagesSection(
                    images = product.images,
                    modifier = Modifier.fillMaxWidth(0.5F),
                    pagerModifier = Modifier
                        .fillMaxSize()
                        .weight(1F)
                )
                DetailsSection(
                    product = product,
                    onAddToCartClicked = onAddToCartClicked,
                    onCartClicked = onCartClicked,
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ImagesSection(
    images: List<String>,
    modifier: Modifier = Modifier,
    pagerModifier: Modifier = Modifier
) {
    val pageCount = images.size
    val pagerState = rememberPagerState(initialPage = 0) { pageCount }

    Column(modifier) {
        Surface(
            shape = MaterialTheme.shapes.large,
            shadowElevation = 8.dp,
            modifier = pagerModifier
        ) {
            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = pageCount,
                pageSpacing = 2.dp,
            ) { pageIndex ->
                ProductAsyncImage(
                    imageUrl = images[pageIndex],
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.large)
                )
            }
        }
        DotIndicators(
            pageCount = pageCount,
            selectedPage = pagerState.currentPage,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun DotIndicators(
    pageCount: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color.DarkGray,
    unselectedColor: Color = Color.LightGray,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(pageCount) { dotNum ->
            val color = if (selectedPage == dotNum) selectedColor else unselectedColor
            Box(
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
fun DetailsSection(
    product: Product,
    onAddToCartClicked: (Product) -> Unit,
    onCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) = with(product) {
    Column(modifier) {
        val hasDiscount = price.discount.active
        val stockResId: Int
        val stockColor: Color
        if (inStock) {
            stockResId = R.string.product_in_stock
            stockColor = DarkGreen
        } else {
            stockResId = R.string.product_out_stock
            stockColor = Color.Red
        }

        // Product name
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )

        // Product rating
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            RatingBar(
                value = rating.stars,
                config = RatingBarConfig()
                    .activeColor(MaterialTheme.colorScheme.primary)
                    .inactiveColor(MaterialTheme.colorScheme.inversePrimary)
                    .size(20.dp),
                onValueChange = {},
                onRatingChanged = {}
            )
            Text(
                text = "(${rating.raters})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        // Stock label
        Text(text = stringResource(stockResId), color = stockColor)

        // Price & discount text & Add to cart button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Price & discount texts
            Column(
                Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
            ) {
                Text(text = price.raw, style = MaterialTheme.typography.headlineMedium)

                if (hasDiscount) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = price.originalRaw,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.headlineSmall.copy(textDecoration = TextDecoration.LineThrough),
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "-${price.discount.percentage}%",
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            modifier = Modifier
                                .background(DarkGreen)
                                .padding(4.dp)
                        )
                    }
                }
            }

            // Add to cart button
            Button(
                enabled = inStock,
                onClick = { onAddToCartClicked(product) }
            ) {
                Text(stringResource(R.string.add_to_cart))
            }
        }

        // Product description
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 32.dp)
                .fillMaxWidth()
        )

        // Proceed to cart button
        Button(
            onClick = onCartClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.proceed_to_cart))
        }

    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@DevicePreviews
@Composable
fun ProductDetailsScreenPreview() {
    ChickyCartThemePreview {
        val product = ProductFakeDataSource().product1
        ProductDetailsScreen(product, { }, { })
    }
}
