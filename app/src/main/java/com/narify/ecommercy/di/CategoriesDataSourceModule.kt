package com.narify.ecommercy.di

import com.narify.ecommercy.data.CategoriesDataSource
import com.narify.ecommercy.data.FakeCategoriesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CategoriesDataSourceModule {

    @Binds
    fun bindsCategoriesDataSource(
        fakeCategoriesDataSource: FakeCategoriesDataSource
    ): CategoriesDataSource
}
