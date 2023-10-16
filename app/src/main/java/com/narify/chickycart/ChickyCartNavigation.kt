package com.narify.chickycart

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.narify.chickycart.ChickyCartDestinations.CART_ROUTE
import com.narify.chickycart.ChickyCartDestinations.CATEGORIES_ROUTE
import com.narify.chickycart.ChickyCartDestinations.CHECKOUT_ROUTE
import com.narify.chickycart.ChickyCartDestinations.HOME_ROUTE
import com.narify.chickycart.ChickyCartDestinations.PRODUCT_DETAILS_ROUTE
import com.narify.chickycart.ChickyCartDestintationsArgs.CATEGORY_NAME_ARG
import com.narify.chickycart.ChickyCartDestintationsArgs.PRODUCT_ID_ARG
import com.narify.chickycart.ChickyCartScreens.CART_SCREEN
import com.narify.chickycart.ChickyCartScreens.CATEGORIES_SCREEN
import com.narify.chickycart.ChickyCartScreens.CHECKOUT_SCREEN
import com.narify.chickycart.ChickyCartScreens.HOME_SCREEN
import com.narify.chickycart.ChickyCartScreens.PRODUCT_DETAILS_SCREEN
import com.narify.chickycart.ui.cart.CartRoute
import com.narify.chickycart.ui.categories.CategoryRoute
import com.narify.chickycart.ui.checkout.CheckoutRoute
import com.narify.chickycart.ui.home.HomeRoute
import com.narify.chickycart.ui.productdetails.ProductDetailsRoute

private object ChickyCartScreens {
    const val HOME_SCREEN = "home"
    const val CATEGORIES_SCREEN = "category"
    const val CART_SCREEN = "cart"
    const val PRODUCT_DETAILS_SCREEN = "productDetails"
    const val CHECKOUT_SCREEN = "checkout"
}

object ChickyCartDestintationsArgs {
    const val PRODUCT_ID_ARG = "productId"
    const val CATEGORY_NAME_ARG = "categoryName"
}

object ChickyCartDestinations {
    const val HOME_ROUTE = "$HOME_SCREEN?$CATEGORY_NAME_ARG={$CATEGORY_NAME_ARG}"
    const val CATEGORIES_ROUTE = CATEGORIES_SCREEN
    const val CART_ROUTE = CART_SCREEN
    const val PRODUCT_DETAILS_ROUTE = "$PRODUCT_DETAILS_SCREEN/{$PRODUCT_ID_ARG}"
    const val CHECKOUT_ROUTE = CHECKOUT_SCREEN
}

//  Type-safe navigation actions
fun NavController.navigateToHome(categoryName: String? = null) {
    navigate("$HOME_SCREEN?$CATEGORY_NAME_ARG=$categoryName") {
        launchSingleTop = true
    }
}

fun NavController.navigateToCategories() {
    navigate(CATEGORIES_ROUTE) {
        launchSingleTop = true
    }
}

fun NavController.navigateToCart() {
    navigate(CART_ROUTE) {
        launchSingleTop = true
    }
}

fun NavController.navigateToProductDetails(productId: String) {
    navigate("$PRODUCT_DETAILS_SCREEN/$productId") {
        launchSingleTop = true
    }
}

fun NavController.navigateToCheckout() {
    navigate(CHECKOUT_ROUTE) {
        launchSingleTop = true
        restoreState = true
    }
}

// Type-safe navigation route builders
fun NavGraphBuilder.homeRoute(onProductClicked: (String) -> Unit) {
    val categoryArg = navArgument(CATEGORY_NAME_ARG) {
        type = NavType.StringType
        nullable = true
    }
    composable(HOME_ROUTE, listOf(categoryArg)) {
        HomeRoute(onProductClicked)
    }
}

fun NavGraphBuilder.categoriesRoute(onCategoryClicked: (String) -> Unit) {
    composable(CATEGORIES_ROUTE) {
        CategoryRoute(onCategoryClicked)
    }
}

fun NavGraphBuilder.cartRoute(onCartItemClicked: (String) -> Unit, onCheckoutClicked: () -> Unit) {
    composable(CART_ROUTE) {
        CartRoute(onCartItemClicked, onCheckoutClicked)
    }
}

fun NavGraphBuilder.productDetailsRoute(onCartClicked: () -> Unit) {
    composable(PRODUCT_DETAILS_ROUTE) {
        ProductDetailsRoute(onCartClicked)
    }
}

fun NavGraphBuilder.checkoutRoute() {
    composable(CHECKOUT_ROUTE) {
        CheckoutRoute()
    }
}
