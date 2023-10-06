package com.narify.ecommercy.ui.productdetails

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.ecommercy.R
import com.narify.ecommercy.data.products.fake.ProductFakeDataSource
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.ui.EmptyContent
import com.narify.ecommercy.ui.theme.EcommercyTheme

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreen(
    product: Product,
    onAddToCartClicked: (Product) -> Unit,
    onCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(Modifier.padding(16.dp)) {
            /* Images slider */
            val pageCount = product.images.size
            val pagerState = rememberPagerState(initialPage = 0) { pageCount }
            Surface(
                shape = MaterialTheme.shapes.large,
                shadowElevation = 8.dp,
            ) {
                HorizontalPager(
                    state = pagerState,
                    beyondBoundsPageCount = pageCount,
                    pageSpacing = 2.dp,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                ) { pageIndex ->
                    AsyncImage(
                        model = product.images[pageIndex],
                        placeholder = painterResource(R.drawable.sample_product_item),
                        contentDescription = stringResource(R.string.content_description_product_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.large)
                    )
                }
            }

            DotIndicators(
                pageCount = pageCount,
                selectedPage = pagerState.currentPage,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            /* Product name */
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            /* Product rating */
            Row(Modifier.padding(vertical = 8.dp)) {
                RatingBar(
                    value = product.rating.stars,
                    config = RatingBarConfig()
                        .activeColor(MaterialTheme.colorScheme.primary)
                        .inactiveColor(MaterialTheme.colorScheme.inversePrimary)
                        .size(20.dp),
                    onValueChange = {},
                    onRatingChanged = {},
                    modifier = modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "(${product.rating.raters})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = modifier
                        .padding(horizontal = 4.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            /* Price text & Add to cart button */
            Row(modifier.padding(vertical = 8.dp)) {
                Text(
                    text = product.price.raw,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )
                Button(onClick = { onAddToCartClicked(product) }) {
                    Text(stringResource(R.string.add_to_cart))
                }
            }

            /* Product description */
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp)
                    .fillMaxWidth()
            )
        }

        /* Proceed to cart button */
        Button(
            onClick = onCartClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(stringResource(R.string.proceed_to_cart))
        }
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
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductDetailsScreenPreview() {
    EcommercyTheme {
        val product = ProductFakeDataSource().product1
        ProductDetailsScreen(product, {}, {})
    }
}
