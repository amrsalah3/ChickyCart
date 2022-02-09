package com.narify.ecommerce.di

import com.narify.ecommerce.data.remote.api.ProductApi
import com.narify.ecommerce.data.remote.api.RealtimeDbProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductApiModule {
    @Provides
    @Singleton
    fun provideProductApi(): ProductApi = RealtimeDbProductService()
}