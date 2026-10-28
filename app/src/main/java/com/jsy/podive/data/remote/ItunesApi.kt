package com.jsy.podive.data.remote

import com.jsy.podive.data.remote.dao.ItunesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET("search")
    suspend fun searchPodcasts(
        @Query("term") term: String,
        @Query("media") media: String = "podcast",
        @Query("entity") entity: String = "podcast",
        @Query("country") country: String = "KR",
        @Query("limit") limit: Int = 20,
    ): ItunesSearchResponse
}