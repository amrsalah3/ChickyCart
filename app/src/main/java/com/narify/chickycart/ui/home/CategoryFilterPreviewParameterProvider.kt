package com.narify.chickycart.ui.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class CategoryFilterPreviewParameterProvider : PreviewParameterProvider<CategoryFilterState?> {
    override val values: Sequence<CategoryFilterState?>
        get() = sequenceOf(null, CategoryFilterState("Laptops", {}))
}
