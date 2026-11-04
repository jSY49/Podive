package com.jsy.podive.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val BASE_URL = "https://itunes.apple.com/"

    @Provides
    @Singleton
    fun provideJson() = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addNetworkInterceptor { chain ->
            // iTunes는 JSON을 text/javascript로 내려줘서 컨버터가 못 읽기 때문에 교체 작업이 필요함.
            chain.proceed(chain.request())
                .newBuilder()
                .header(
                    "Content-Type",
                    "application/json; charset=utf-8"
                )
                .build()
        }
        .build()

    @Provides
    @Singleton
    fun provideItunesApi(client: OkHttpClient, json: Json): ItunesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ItunesApi::class.java)
}