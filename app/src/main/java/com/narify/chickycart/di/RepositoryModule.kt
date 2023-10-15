package com.narify.chickycart.di

import com.narify.chickycart.data.cart.CartDefaultRepository
import com.narify.chickycart.data.cart.CartRepository
import com.narify.chickycart.data.categories.CategoriesDefaultRepository
import com.narify.chickycart.data.categories.CategoriesRepository
import com.narify.chickycart.data.checkout.CheckoutDefaultRepository
import com.narify.chickycart.data.checkout.CheckoutRepository
import com.narify.chickycart.data.products.ProductDefaultRepository
import com.narify.chickycart.data.products.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsProductRespository(
        productRepository: ProductDefaultRepository
    ): ProductRepository

    @Binds
    fun bindsCategoriesRespository(
        categoriesRepository: CategoriesDefaultRepository
    ): CategoriesRepository

    @Binds
    fun bindsCartRespository(
        cartRepository: CartDefaultRepository
    ): CartRepository

    @Binds
    fun bindsCheckoutRespository(
        checkoutRepository: CheckoutDefaultRepository
    ): CheckoutRepository
}
