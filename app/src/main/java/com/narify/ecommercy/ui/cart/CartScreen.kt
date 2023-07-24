package com.narify.ecommercy.ui.cart

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.narify.ecommercy.R
import com.narify.ecommercy.data.FakeCartDataSource
import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.totalPriceText
import com.narify.ecommercy.ui.LoadingContent
import com.narify.ecommercy.ui.theme.EcommercyTheme

@Composable
fun CartRoute(
    onCheckoutClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) LoadingContent()
    else CartScreen(
        cartItems = uiState.cartItems,
        onIncrementItem = { viewModel.increaseItemCount(it.product) },
        onDecrementItem = { viewModel.decreaseItemCount(it.product.id) },
        onCheckoutClicked = onCheckoutClicked
    )
}

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    onCheckoutClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        CartItemsList(
            cartItems = cartItems,
            onIncrementItem = onIncrementItem,
            onDecrementItem = onDecrementItem,
            modifier = Modifier.weight(1f)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Button(
                onClick = onCheckoutClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text("PROCEED TO CHECKOUT")
            }
        }
    }
}

@Composable
fun CartItemsList(
    cartItems: List<CartItem>,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        modifier = modifier
    ) {
        items(cartItems) { item ->
            CartItem(
                cartItemState = item,
                onIncrementItem = onIncrementItem,
                onDecrementItem = onDecrementItem,
                cardColor = cardColor
            )
        }
    }
}

@Composable
fun CartItem(
    cartItemState: CartItem,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = cardColor,
        shadowElevation = 6.dp,
    ) {
        Row(
            modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = cartItemState.product.getThumbnail(),
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
                    text = cartItemState.product.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = cartItemState.totalPriceText,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Button(onClick = { onDecrementItem(cartItemState) }) {
                        Text(
                            text = "-",
                            fontSize = 20.sp,
                        )
                    }
                    Text(
                        text = "${cartItemState.count}",
                        fontSize = 20.sp,
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Button(onClick = { onIncrementItem(cartItemState) }) {
                        Text(
                            text = "+",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenPreview() {
    EcommercyTheme {
        val cartItems = FakeCartDataSource().getPreviewCartItems()
        CartScreen(cartItems, {}, {}, {})
    }
}
