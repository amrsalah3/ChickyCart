package com.narify.ecommercy.ui.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.narify.ecommercy.data.categories.fake.CategoriesFakeDataSource
import com.narify.ecommercy.ui.EmptyContent
import com.narify.ecommercy.ui.common.DevicePreviews
import com.narify.ecommercy.ui.common.LoadingContent
import com.narify.ecommercy.ui.theme.EcommercyThemePreview

@Composable
fun CategoryRoute(
    onCategoryClicked: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) LoadingContent()
    else if (uiState.errorState.hasError) EmptyContent(uiState.errorState.errorMsgResId)
    else CategoryScreen(uiState.categoryItems, onCategoryClicked)
}

@Composable
fun CategoryScreen(
    categoryItems: List<CategoryItemUiState>,
    onCategoryClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(categoryItems) { category ->
            CategoryCard(category, onCategoryClicked)
        }
    }
}

@Composable
fun CategoryCard(
    categoryState: CategoryItemUiState,
    onCategoryClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = cardColor,
        shadowElevation = 6.dp,
        modifier = modifier.size(120.dp)
    ) {
        Box(Modifier.clickable { onCategoryClicked(categoryState.name) }) {
            Text(
                text = categoryState.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@DevicePreviews
@Composable
fun CategoryScreenPreview() {
    EcommercyThemePreview {
        CategoriesFakeDataSource().getPreviewCategories().let {
            val categories = it.toCategoriesUiState()
            CategoryScreen(categories, { })
        }
    }
}
