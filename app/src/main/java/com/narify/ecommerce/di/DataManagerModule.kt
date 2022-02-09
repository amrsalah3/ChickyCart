package com.narify.ecommerce.di

import android.content.Context
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.data.local.DataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataManagerModule {
    @Provides
    @Singleton
    fun provideDataManager(@ApplicationContext context: Context): DataManager =
        AppPreferences(context)
}