package com.narify.ecommercy.di

import com.narify.ecommercy.data.cart.CartDataSource
import com.narify.ecommercy.data.cart.FakeCartDataSource
import com.narify.ecommercy.data.categories.CategoriesDataSource
import com.narify.ecommercy.data.categories.FakeCategoriesDataSource
import com.narify.ecommercy.data.order.FakeOrderService
import com.narify.ecommercy.data.order.FakeReceiptDataSource
import com.narify.ecommercy.data.order.OrderService
import com.narify.ecommercy.data.order.ReceiptDataSource
import com.narify.ecommercy.data.products.FakeProductsDataSource
import com.narify.ecommercy.data.products.ProductsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsProductsDataSource(
        productsDataSource: FakeProductsDataSource
    ): ProductsDataSource

    @Binds
    fun bindsCategoriesDataSource(
        fakeCategoriesDataSource: FakeCategoriesDataSource
    ): CategoriesDataSource

    @Binds
    fun bindsCartDataSource(
        cartDataSource: FakeCartDataSource
    ): CartDataSource

    @Binds
    fun bindsReceiptDataSource(
        receiptDataSource: FakeReceiptDataSource
    ): ReceiptDataSource

    @Binds
    fun bindsOrderService(
        orderService: FakeOrderService
    ): OrderService
}
