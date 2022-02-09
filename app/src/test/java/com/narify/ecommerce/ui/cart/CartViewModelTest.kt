package com.narify.ecommerce.ui.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.narify.ecommerce.model.CartItem
import com.narify.ecommerce.model.Product
import com.narify.ecommerce.model.Result
import com.narify.ecommerce.usecase.AddCartItemUseCase
import com.narify.ecommerce.usecase.GetCartItemsUseCase
import com.narify.ecommerce.usecase.RemoveCartItemUseCase
import com.narify.ecommerce.util.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
internal class CartViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private lateinit var cartViewModel: CartViewModel

    @MockK
    private lateinit var getCartUseCase: GetCartItemsUseCase

    @MockK
    private lateinit var addCartUseCase: AddCartItemUseCase

    @MockK
    private lateinit var removeCartUseCase: RemoveCartItemUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testFetchCartItems() = coroutineRule.runBlockingTest {
        val expected = Result.Success<List<CartItem>>(emptyList())
        coEvery { getCartUseCase.invoke() } returns expected

        cartViewModel = CartViewModel(getCartUseCase, addCartUseCase, removeCartUseCase)

        assertEquals(expected.data, cartViewModel.cartItems.value)
    }

    @Test
    fun `test add product to cart`() = coroutineRule.runBlockingTest {
        val expected = Result.Success<List<CartItem>>(emptyList())
        coEvery { getCartUseCase.invoke() } returns expected
        cartViewModel = CartViewModel(getCartUseCase, addCartUseCase, removeCartUseCase)

        cartViewModel.addProduct(Product(PRODUCT_ID_1))


        assertEquals(expected.data[0].product, cartViewModel.cartItems.value!![0])
    }

    companion object {
        private const val PRODUCT_ID_1 = "-Mgp6kAAY_gJGBvSva0Pr"
        private const val PRODUCT_ID_2 = "-BasfFfdsADFG_vsAVAQV"
    }

}