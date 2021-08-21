package com.narify.ecommerce.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class CartTest {

    private lateinit var cart: Cart

    @BeforeEach
    fun setUp() {
        cart = Cart()
    }

    @Test
    fun should_returnReversedCartItems() {
        val product1 = Product(PRODUCT_ID_1)
        val product2 = Product(PRODUCT_ID_2)
        val count1 = 3
        val count2 = 4
        val item1 = CartItem(product1, count1)
        val item2 = CartItem(product2, count2)

        cart.addProduct(product1, count1)
        cart.addProduct(product2, count2)

        val actualCartItems = cart.getItems()
        val expectedCartItems = listOf(item2, item1)
        assertIterableEquals(expectedCartItems, actualCartItems)
    }

    @Test
    fun should_addNewItemToCart_when_productIsAbsent() {
        val product = Product(PRODUCT_ID_1)
        val count = 3
        val productCartItem = CartItem(product, count)

        cart.addProduct(product, count)

        val actualProductCartItem = cart.getItems()[0]
        assertEquals(productCartItem, actualProductCartItem)

        val actualCartSize = cart.getItems().size
        assertEquals(1, actualCartSize)
    }

    @Test
    fun should_increaseProductCountInCartItem_when_productIsPresent() {
        val product = Product(PRODUCT_ID_1)
        val sameProduct = Product(PRODUCT_ID_1)

        cart.addProduct(product, 1)
        cart.addProduct(sameProduct, 4)

        val actualProductItemCount = cart.getItems()[0].count
        assertEquals(5, actualProductItemCount)

        val actualCartSize = cart.getItems().size
        assertEquals(1, actualCartSize)
    }

    @Test
    fun should_removeProductFromCart_when_countOne() {
        val product = Product(PRODUCT_ID_1)
        cart.addProduct(product, 1)

        cart.removeProduct(product, 3)

        val actualCartSize = cart.getItems().size
        assertEquals(0, actualCartSize)
    }

    @Test
    fun should_decreaseProductCountInCartItem_when_moreThanOne() {
        val product = Product(PRODUCT_ID_1)
        cart.addProduct(product, 5)

        cart.removeProduct(product, 3)

        val actualProductItemCount = cart.getItems()[0].count
        assertEquals(2, actualProductItemCount)

        val actualCartSize = cart.getItems().size
        assertEquals(1, actualCartSize)
    }


    companion object {
        private const val PRODUCT_ID_1 = "-Mgp6kAAY_gJGBvSva0Pr"
        private const val PRODUCT_ID_2 = "-BasfFfdsADFG_vsAVAQV"
    }
}