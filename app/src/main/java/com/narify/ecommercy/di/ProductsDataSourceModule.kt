package com.narify.ecommercy.di

import com.narify.ecommercy.data.FakeProductsDataSource
import com.narify.ecommercy.data.ProductsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProductsDataSourceModule {

    @Binds
    fun bindsProductsDataSource(
        productsDataSource: FakeProductsDataSource
    ): ProductsDataSource
}
