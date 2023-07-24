package com.narify.ecommercy

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.narify.ecommercy.EcommercyDestinations.CART_ROUTE
import com.narify.ecommercy.EcommercyDestinations.CATEGORIES_ROUTE
import com.narify.ecommercy.EcommercyDestinations.HOME_ROUTE
import com.narify.ecommercy.ui.theme.EcommercyTheme

@Composable
fun EcommercyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE
) {
    Scaffold(
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val activeDestination = backStackEntry?.destination?.route ?: HOME_ROUTE
            BottomBar(
                onHomeTabClicked = { navController.navigateToHome() },
                onCategoriesTabClicked = { navController.navigateToCategories() },
                onCartTabClicked = { navController.navigateToCart() },
                selectedItem = activeDestination,
                modifier = Modifier.height(60.dp)
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)
        ) {
            homeRoute()
            categoriesRoute()
            cartRoute(onCheckoutClicked = { navController.navigateToCheckout() })
            productDetailsRoute()
            checkoutRoute()
        }
    }
}

@Composable
fun BottomBar(
    onHomeTabClicked: () -> Unit,
    onCategoriesTabClicked: () -> Unit,
    onCartTabClicked: () -> Unit,
    modifier: Modifier = Modifier,
    selectedItem: String = HOME_ROUTE,
) {
    NavigationBar(modifier) {
        NavigationBarItem(
            selected = selectedItem == HOME_ROUTE,
            onClick = onHomeTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home screen"
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == CATEGORIES_ROUTE,
            onClick = onCategoriesTabClicked,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_category),
                    contentDescription = "Categories screen"
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == CART_ROUTE,
            onClick = onCartTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart screen"
                )
            }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EcommercyBottomBarPreview() {
    EcommercyTheme {
        BottomBar(
            onHomeTabClicked = { },
            onCategoriesTabClicked = { },
            onCartTabClicked = { }
        )
    }
}
