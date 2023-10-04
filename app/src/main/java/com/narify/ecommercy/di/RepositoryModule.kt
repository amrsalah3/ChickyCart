package com.narify.ecommercy.di

import com.narify.ecommercy.data.cart.CartDefaultRepository
import com.narify.ecommercy.data.cart.CartRepository
import com.narify.ecommercy.data.categories.CategoriesDefaultRepository
import com.narify.ecommercy.data.categories.CategoriesRepository
import com.narify.ecommercy.data.products.ProductDefaultRepository
import com.narify.ecommercy.data.products.ProductRepository
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
}
