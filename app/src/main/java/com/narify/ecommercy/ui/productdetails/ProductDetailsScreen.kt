package com.narify.ecommercy.ui.productdetails

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.narify.ecommercy.R
import com.narify.ecommercy.model.Product
import com.narify.ecommercy.ui.LoadingContent
import com.narify.ecommercy.ui.theme.EcommercyTheme

@Composable
fun ProductDetailsRoute(
    onCartClicked: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (uiState.isLoading) LoadingContent()
    else ProductDetailsScreen(
        uiState.product!!,
        onAddToCartClicked = {
            viewModel.addProductToCart(it)
            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
        },
        onCartClicked = onCartClicked
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreen(
    product: Product,
    onAddToCartClicked: (Product) -> Unit,
    onCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        val pageCount = product.images.size
        val pagerState = rememberPagerState(initialPage = 0) { pageCount }
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(300.dp)
        ) { pageIndex ->
            Box {
                AsyncImage(
                    model = product.images[pageIndex],
                    placeholder = painterResource(R.drawable.sample_product_item),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.clip(MaterialTheme.shapes.large)
                )
                DotIndicators(
                    pageCount = pageCount,
                    selectedPage = pagerState.currentPage,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = product.price.raw,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { onAddToCartClicked(product) }) {
                Text("Add to cart")
            }
        }

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = onCartClicked, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Proceed to cart")
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
    Row(modifier = modifier) {
        repeat(pageCount) { dotNum ->
            val color = if (selectedPage == dotNum) selectedColor else unselectedColor
            Box(
                Modifier
                    .padding(horizontal = 8.dp)
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
        ProductDetailsRoute({})
    }
}
