package com.narify.ecommercy.ui.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.narify.ecommercy.R
import com.narify.ecommercy.data.cart.fake.CartFakeDataSource
import com.narify.ecommercy.model.CartItem
import com.narify.ecommercy.model.totalPriceText
import com.narify.ecommercy.ui.EmptyContent
import com.narify.ecommercy.ui.common.DevicePreviews
import com.narify.ecommercy.ui.common.LoadingContent
import com.narify.ecommercy.ui.common.ProductAsyncImage
import com.narify.ecommercy.ui.common.itemWithMaxWidth
import com.narify.ecommercy.ui.theme.EcommercyThemePreview

@Composable
fun CartRoute(
    onCartItemClicked: (String) -> Unit,
    onCheckoutClicked: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) LoadingContent()
    else if (uiState.errorState.hasError) EmptyContent(uiState.errorState.errorMsgResId)
    else if (uiState.cartItems.isEmpty()) EmptyContent(R.string.empty_cart)
    else CartScreen(
        cartItems = uiState.cartItems,
        onIncrementItem = { viewModel.increaseItemCount(it.product) },
        onDecrementItem = { viewModel.decreaseItemCount(it.product.id) },
        onCartItemClicked = onCartItemClicked,
        onCheckoutClicked = onCheckoutClicked,
    )
}

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    onCartItemClicked: (String) -> Unit,
    onCheckoutClicked: () -> Unit
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val columns: Int
        val gridWidthFraction: Float
        if (maxWidth < 450.dp) {
            // Normal available space
            columns = 1
            gridWidthFraction = 1F
        } else {
            // Large available space
            if (cartItems.size == 1) {
                // Add only one column to the grid and do not stretch its width to the maximum
                columns = 1
                gridWidthFraction = 0.6F
            } else {
                // Add two columns to the grid and expand its width to the maximum
                columns = 2
                gridWidthFraction = 1F
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(gridWidthFraction)
        ) {
            items(cartItems.size) { index ->
                CartItem(
                    cartItemState = cartItems[index],
                    onIncrementItem = onIncrementItem,
                    onDecrementItem = onDecrementItem,
                    onCartItemClicked = onCartItemClicked
                )
            }

            itemWithMaxWidth {
                Button(
                    onClick = onCheckoutClicked,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Text(stringResource(R.string.proceed_to_checkout))
                }
            }
        }
    }

}

@Composable
fun CartItem(
    cartItemState: CartItem,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    onCartItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer
) = with(cartItemState) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = cardColor,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier
                .height(150.dp)
                .fillMaxWidth()
                .clickable(true) {
                    onCartItemClicked(product.id)
                }
        ) {
            ProductAsyncImage(product.thumbnail, Modifier.weight(1.2f))
            Column(
                modifier
                    .padding(16.dp)
                    .weight(2f)
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = totalPriceText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        shape = MaterialTheme.shapes.small,
                        onClick = { onDecrementItem(cartItemState) },
                    ) {
                        Text(
                            text = "-",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "$count",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Button(
                        shape = MaterialTheme.shapes.small,
                        onClick = { onIncrementItem(cartItemState) }
                    ) {
                        Text(
                            text = "+",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun CartScreenPreview() {
    EcommercyThemePreview {
        val cartItems = CartFakeDataSource().getPreviewCartItems().subList(0, 1)
        CartScreen(cartItems, { }, { }, { }, { })
    }
}
