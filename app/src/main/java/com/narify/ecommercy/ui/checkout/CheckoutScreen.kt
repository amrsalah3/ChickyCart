package com.narify.ecommercy.ui.checkout

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.narify.ecommercy.R
import com.narify.ecommercy.data.checkout.fake.ReceiptFakeDataSource
import com.narify.ecommercy.ui.EmptyContent
import com.narify.ecommercy.ui.common.LoadingContent

@Composable
fun CheckoutRoute(viewModel: CheckoutViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) LoadingContent()
    else if (uiState.errorState.hasError) EmptyContent(uiState.errorState.errorMsgResId)
    else if (uiState.ordering != null) {
        when (uiState.ordering!!) {
            OrderingUiState.OrderLoading -> PlacingOrderLoading()
            OrderingUiState.OrderPlaced -> OrderResult(
                resultMsg = stringResource(R.string.order_result_success),
                imageRes = R.drawable.ic_success,
                imageTint = Color(0xFFA4FA3C)
            )

            is OrderingUiState.OrderFailed -> OrderResult(
                resultMsg = stringResource(R.string.order_result_error),
                imageRes = R.drawable.ic_error,
                imageTint = Color(0xFFFA0000)
            )
        }
    } else {
        val scrollState = rememberScrollState()

        CheckoutScreen(
            shippingInputState = uiState.shippingInputState,
            shippingErrorState = uiState.shippingErrorState,
            shippingOnValueChange = viewModel::onShippingUiEvent,
            receiptItemsState = uiState.receiptItemsState,
            onPlaceOrderClicked = viewModel::placeOrder,
            scrollState = scrollState
        )

        LaunchedEffect(uiState.shouldScrollToShowError) {
            if (uiState.shouldScrollToShowError) {
                try {
                    val scrollPosition = 100
                    scrollState.animateScrollTo(
                        scrollPosition,
                        SpringSpec(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
                    )
                } finally {
                    // Set scrolled even if the user cancels the animation (and its coroutine)
                    viewModel.setScrolled()
                }
            }
        }
    }
}

@Composable
fun CheckoutScreen(
    shippingInputState: ShippingInputState,
    shippingErrorState: ShippingErrorState,
    shippingOnValueChange: (ShippingUiEvent) -> Unit,
    receiptItemsState: List<ReceiptUiItemState>,
    onPlaceOrderClicked: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SectionCard(stringResource(R.string.section_shipping_details)) {
            Column(Modifier.padding(16.dp)) {
                ShippingTextField(
                    value = shippingInputState.name,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.NameChanged(it))
                    },
                    label = stringResource(R.string.tf_label_name),
                    placeholder = stringResource(R.string.tf_placeholder_name),
                    isError = shippingErrorState.name.hasError,
                )

                ShippingTextField(
                    value = shippingInputState.email,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.EmailChanged(it))
                    },
                    label = stringResource(R.string.tf_label_email),
                    placeholder = stringResource(R.string.tf_placeholder_email),
                    isError = shippingErrorState.email.hasError,
                    keyboardType = KeyboardType.Email
                )

                ShippingTextField(
                    value = shippingInputState.mobileNumber,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.MobileNumberChanged(it))
                    },
                    label = stringResource(R.string.tf_label_mobile_number),
                    placeholder = stringResource(R.string.tf_placeholder_mobile_number),
                    isError = shippingErrorState.mobileNumber.hasError,
                    keyboardType = KeyboardType.Phone
                )

                ShippingTextField(
                    value = shippingInputState.country,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.CountryChanged(it))
                    },
                    label = stringResource(R.string.tf_label_country),
                    placeholder = stringResource(R.string.tf_placeholder_country),
                    isError = shippingErrorState.country.hasError
                )

                ShippingTextField(
                    value = shippingInputState.state,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.StateChanged(it))
                    },
                    label = stringResource(R.string.tf_label_state),
                    placeholder = stringResource(R.string.tf_placeholder_state),
                    isError = shippingErrorState.state.hasError
                )

                ShippingTextField(
                    value = shippingInputState.city,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.CityChanged(it))
                    },
                    label = stringResource(R.string.tf_label_city),
                    placeholder = stringResource(R.string.tf_placeholder_city),
                    isError = shippingErrorState.city.hasError
                )

                ShippingTextField(
                    value = shippingInputState.address,
                    onValueChange = {
                        shippingOnValueChange(ShippingUiEvent.AddressChanged(it))
                    },
                    label = stringResource(R.string.tf_label_address),
                    placeholder = stringResource(R.string.tf_placeholder_address),
                    isError = shippingErrorState.address.hasError,
                    imeAction = ImeAction.Done
                )
            }
        }

        SectionCard(stringResource(R.string.section_receipt)) {
            Column(Modifier.padding(16.dp)) {
                // Header receipt description
                ReceiptUiItem(
                    stringResource(R.string.receipt_item_description),
                    stringResource(R.string.receipt_item_cost),
                    fontWeight = FontWeight.Bold
                )
                // Content of the receipt
                receiptItemsState.forEach { item ->
                    val fontWeight = if (item.name.equals("total", true)) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                    ReceiptUiItem(label = item.name, cost = item.price, fontWeight = fontWeight)
                }
            }
        }

        Button(
            onClick = onPlaceOrderClicked,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(82.dp)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.place_the_order))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShippingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        label = {
            Text(label)
        },
        placeholder = {
            Text(placeholder)
        },
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ReceiptUiItem(
    label: String,
    cost: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Row(modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = fontWeight, modifier = Modifier.weight(1f))
        Text(text = cost, fontWeight = fontWeight)
    }
}

@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.padding(16.dp)
        )
        content()
    }
}

@Composable
fun OrderResult(
    resultMsg: String,
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    imageTint: Color = Color.Unspecified
) {
    Column(modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(imageRes),
            contentDescription = resultMsg,
            tint = imageTint,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Text(
            text = resultMsg,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = imageTint,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 64.dp)

        )
    }
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(
        shippingInputState = ShippingInputState(),
        shippingErrorState = ShippingErrorState(),
        shippingOnValueChange = { },
        receiptItemsState = ReceiptFakeDataSource().getPreviewReceiptItems()
            .toReceiptUiItemsState(),
        onPlaceOrderClicked = { }
    )
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderResultSuccessPreview() {
    OrderResult(
        resultMsg = stringResource(R.string.order_result_success),
        imageRes = R.drawable.ic_success,
        imageTint = Color(0xFFA4FA3C)
    )
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderResultFailPreview() {
    OrderResult(
        resultMsg = stringResource(R.string.order_result_error),
        imageRes = R.drawable.ic_error,
        imageTint = Color(0xFFFA0000)
    )
}
