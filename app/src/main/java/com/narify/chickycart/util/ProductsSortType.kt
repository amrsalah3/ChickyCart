package com.narify.chickycart.util

import androidx.annotation.StringRes
import com.narify.chickycart.R

/**
 * Enum representing different sorting criteria for products.
 *
 * @property labelResId String resource ID of the label associated with this sorting criteria.
 */
enum class ProductsSortType(@StringRes val labelResId: Int) {
    /**
     * Sort in alphabetical order.
     */
    ALPHABETICAL(R.string.sort_alphabetical),

    /**
     * Sort by price.
     */
    PRICE(R.string.sort_price),

    /**
     * Sort by rating.
     */
    RATING(R.string.sort_rating),

    /**
     * Do not apply any sorting criteria.
     */
    NONE(R.string.sort_none)
}

