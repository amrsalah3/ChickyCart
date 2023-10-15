package com.narify.chickycart.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable

/**
 * Composes a LazyGrid item which occupies the max span (max width for vertical grids,
 * or max height for horizontal grids) to be used as a header or footer item.
 */
fun LazyGridScope.itemWithMaxWidth(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }) {
        Column {
            content()
        }
    }
}
