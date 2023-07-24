package com.narify.ecommercy.di

import com.narify.ecommercy.data.CartDataSource
import com.narify.ecommercy.data.FakeCartDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CartDataSourceModule {

    @Binds
    fun bindsCartDataSource(
        cartDataSource: FakeCartDataSource
    ): CartDataSource
}
