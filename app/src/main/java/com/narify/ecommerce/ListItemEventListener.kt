package com.narify.ecommerce

interface ListItemEventListener<Item> {
    fun onItemClicked(item: Item)
}