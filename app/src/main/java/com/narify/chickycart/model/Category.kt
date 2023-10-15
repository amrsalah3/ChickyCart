package com.narify.chickycart.model

data class Category(val id: String, val name: String)

fun createCategory(name: String): Category {
    val nameWithFirstCharCapitalized = name.replaceFirstChar { it.uppercaseChar() }
    return Category(id = nameWithFirstCharCapitalized, name = nameWithFirstCharCapitalized)
}
