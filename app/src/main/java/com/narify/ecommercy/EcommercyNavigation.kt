package com.narify.ecommercy

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.narify.ecommercy.EcommercyDestinations.CART_ROUTE
import com.narify.ecommercy.EcommercyDestinations.CATEGORIES_ROUTE
import com.narify.ecommercy.EcommercyDestinations.CHECKOUT_ROUTE
import com.narify.ecommercy.EcommercyDestinations.HOME_ROUTE
import com.narify.ecommercy.EcommercyDestinations.PRODUCT_DETAILS_ROUTE
import com.narify.ecommercy.EcommercyDestintationsArgs.CATEGORY_NAME_ARG
import com.narify.ecommercy.EcommercyDestintationsArgs.PRODUCT_ID_ARG
import com.narify.ecommercy.EcommercyScreens.CART_SCREEN
import com.narify.ecommercy.EcommercyScreens.CATEGORIES_SCREEN
import com.narify.ecommercy.EcommercyScreens.CHECKOUT_SCREEN
import com.narify.ecommercy.EcommercyScreens.HOME_SCREEN
import com.narify.ecommercy.EcommercyScreens.PRODUCT_DETAILS_SCREEN
import com.narify.ecommercy.EcommercyScreens.SETTINGS_SCREEN
import com.narify.ecommercy.ui.cart.CartRoute
import com.narify.ecommercy.ui.categories.CategoryRoute
import com.narify.ecommercy.ui.checkout.CheckoutRoute
import com.narify.ecommercy.ui.home.HomeRoute
import com.narify.ecommercy.ui.productdetails.ProductDetailsRoute

private object EcommercyScreens {
    const val HOME_SCREEN = "home"
    const val CATEGORIES_SCREEN = "category"
    const val CART_SCREEN = "cart"
    const val SETTINGS_SCREEN = "settings"
    const val PRODUCT_DETAILS_SCREEN = "productDetails"
    const val CHECKOUT_SCREEN = "checkout"
}

object EcommercyDestintationsArgs {
    const val PRODUCT_ID_ARG = "productId"
    const val CATEGORY_NAME_ARG = "categoryName"
}

object EcommercyDestinations {
    const val HOME_ROUTE = "$HOME_SCREEN?$CATEGORY_NAME_ARG={$CATEGORY_NAME_ARG}"
    const val CATEGORIES_ROUTE = CATEGORIES_SCREEN
    const val CART_ROUTE = CART_SCREEN
    const val SETTINGS_ROUTE = SETTINGS_SCREEN
    const val PRODUCT_DETAILS_ROUTE = "$PRODUCT_DETAILS_SCREEN/{$PRODUCT_ID_ARG}"
    const val CHECKOUT_ROUTE = CHECKOUT_SCREEN
}

//  Type-safe navigation actions
fun NavController.navigateToHome(categoryName: String? = null, restoreHome: Boolean = true) {
    navigate("$HOME_SCREEN?$CATEGORY_NAME_ARG=$categoryName") {
        popUpTo(HOME_ROUTE) {
            inclusive = !restoreHome
            saveState = true
        }
        launchSingleTop = true
        restoreState = restoreHome
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

fun NavController.navigateToProductDetails(productId: String) {
    navigate("$PRODUCT_DETAILS_SCREEN/$productId") {
        popUpTo(HOME_ROUTE) {
            saveState = true
        }
        launchSingleTop = true
    }
}

fun NavController.navigateToCheckout() {
    navigate(CHECKOUT_ROUTE) {
        popUpTo(CART_ROUTE) {
            saveState = true
        }
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

fun NavGraphBuilder.cartRoute(onCheckoutClicked: () -> Unit) {
    composable(CART_ROUTE) {
        CartRoute(onCheckoutClicked)
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
