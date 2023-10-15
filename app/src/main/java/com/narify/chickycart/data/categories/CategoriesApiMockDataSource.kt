package com.narify.chickycart.data.categories

import com.narify.chickycart.model.Category
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesApiMockDataSource @Inject constructor() {

    private val categories = listOf(
        Category(id = "1", name = "Smartphones"),
        Category(id = "2", name = "Laptops"),
        Category(id = "3", name = "Fragrances"),
        Category(id = "4", name = "Skincare"),
        Category(id = "5", name = "Groceries"),
        Category(id = "6", name = "Home Decoration"),
        Category(id = "7", name = "Furniture"),
        Category(id = "8", name = "Tops"),
        Category(id = "9", name = "Women Dresses"),
        Category(id = "10", name = "Women Shoes"),
        Category(id = "11", name = "Men Shirts"),
        Category(id = "12", name = "Men Shoes"),
        Category(id = "13", name = "Men Watches"),
        Category(id = "14", name = "Women Watches"),
        Category(id = "15", name = "Women Bags"),
        Category(id = "16", name = "Women Jewellery"),
        Category(id = "17", name = "Automotive"),
        Category(id = "18", name = "Sunglasses"),
        Category(id = "19", name = "Motorcycle"),
        Category(id = "20", name = "Lighting")
    )

    suspend fun getCategories(): List<Category> {
        delay(500)
        return categories
    }
}

