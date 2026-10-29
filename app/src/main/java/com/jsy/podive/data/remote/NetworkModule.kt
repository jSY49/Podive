package com.jsy.podive.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object NetworkModule {

    private val json = Json { ignoreUnknownKeys = true }
    private val BASE_URL = "https://itunes.apple.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addNetworkInterceptor { chain ->
            // iTunes는 JSON을 text/javascript로 내려줘서 컨버터가 못 읽기 때문에 교체 작업이 필요함.
            chain.proceed(chain.request()).newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .build()
        }
        .build()

    val itunesApi : ItunesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ItunesApi::class.java)
}