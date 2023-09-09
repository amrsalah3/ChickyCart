package com.narify.ecommercy.di

import com.narify.ecommercy.data.FakeReceiptDataSource
import com.narify.ecommercy.data.ReceiptDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ReceiptDataSourceModule {

    @Binds
    fun bindsReceiptDataSource(
        receiptDataSource: FakeReceiptDataSource
    ): ReceiptDataSource
}
