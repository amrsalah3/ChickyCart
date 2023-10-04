package com.narify.ecommercy.di

import com.narify.ecommercy.data.order.FakeOrderService
import com.narify.ecommercy.data.order.FakeReceiptDataSource
import com.narify.ecommercy.data.order.OrderService
import com.narify.ecommercy.data.order.ReceiptDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsReceiptDataSource(
        receiptDataSource: FakeReceiptDataSource
    ): ReceiptDataSource

    @Binds
    fun bindsOrderService(
        orderService: FakeOrderService
    ): OrderService
}
