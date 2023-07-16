package com.narify.ecommercy.ui.categories

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.narify.ecommercy.data.FakeCategoriesDataSource
import com.narify.ecommercy.ui.LoadingContent
import com.narify.ecommercy.ui.theme.EcommercyTheme

@Composable
fun CategoryRoute(viewModel: CategoriesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) LoadingContent()
    else CategoryScreen(uiState.categoryItems)
}

@Composable
fun CategoryScreen(categoryItems: List<CategoryItemUiState>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(categoryItems) { category ->
            CategoryCard(category)
        }
    }
}

@Composable
fun CategoryCard(
    categoryState: CategoryItemUiState,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.secondaryContainer,
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = cardColor,
        shadowElevation = 6.dp,
        modifier = modifier.size(120.dp)
    ) {
        Box(Modifier.clickable { }) {
            Text(
                text = categoryState.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CategoryScreenPreview() {
    EcommercyTheme {
        FakeCategoriesDataSource().getPreviewCategories().let {
            val categories = it.toCategoriesUiState()
            CategoryScreen(categories)
        }
    }
}
