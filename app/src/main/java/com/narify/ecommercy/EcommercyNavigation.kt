package com.narify.ecommercy

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narify.ecommercy.EcommercyDestinations.CART_ROUTE
import com.narify.ecommercy.EcommercyDestinations.CATEGORIES_ROUTE
import com.narify.ecommercy.EcommercyDestinations.HOME_ROUTE
import com.narify.ecommercy.EcommercyDestinations.PRODUCT_DETAILS_ROUTE
import com.narify.ecommercy.ui.cart.CartRoute
import com.narify.ecommercy.ui.categories.CategoryRoute
import com.narify.ecommercy.ui.home.HomeRoute
import com.narify.ecommercy.ui.productdetails.ProductDetailsRoute

object EcommercyDestinations {
    const val HOME_ROUTE = "home"
    const val CATEGORIES_ROUTE = "category"
    const val CART_ROUTE = "cart"
    const val SETTINGS_ROUTE = "settings"
    const val PRODUCT_DETAILS_ROUTE = "productDetails"
    const val CHECKOUT_ROUTE = "checkout"
}

//  Type-safe navigation actions
fun NavController.navigateToHome() {
    navigate(HOME_ROUTE) {
        popUpTo(HOME_ROUTE) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToCategories() {
    navigate(CATEGORIES_ROUTE) {
        popUpTo(HOME_ROUTE) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToCart() {
    navigate(CART_ROUTE) {
        popUpTo(HOME_ROUTE) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToProductDetails() {
    navigate(PRODUCT_DETAILS_ROUTE) {
        popUpTo(HOME_ROUTE) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

// Type-safe navigation route builders
fun NavGraphBuilder.homeRoute() {
    composable(HOME_ROUTE) {
        HomeRoute()
    }
}

fun NavGraphBuilder.categoriesRoute() {
    composable(CATEGORIES_ROUTE) {
        CategoryRoute()
    }
}

fun NavGraphBuilder.cartRoute() {
    composable(CART_ROUTE) {
        CartRoute()
    }
}

fun NavGraphBuilder.productDetailsRoute() {
    composable(PRODUCT_DETAILS_ROUTE) {
        ProductDetailsRoute()
    }
}
