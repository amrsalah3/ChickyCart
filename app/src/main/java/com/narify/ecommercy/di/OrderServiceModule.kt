package com.narify.ecommercy.di

import com.narify.ecommercy.data.FakeOrderService
import com.narify.ecommercy.data.OrderService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface OrderServiceModule {

    @Binds
    fun bindsOrderService(
        orderService: FakeOrderService
    ): OrderService
}
