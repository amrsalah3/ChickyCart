package com.narify.ecommercy.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object KtorClientModule {

    @Provides
    fun provideHttpClient(): HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                }
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Ktor Logs =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 10_000
        }
    }
}
