package com.jsy.podive.data.remote

import com.jsy.podive.data.remote.dto.ItunesChartResponse
import com.jsy.podive.data.remote.dto.ItunesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("{country}/rss/toppodcasts/limit={limit}/json")
    suspend fun getTopPodcasts(
        @Path("country") country: String = "kr",
        @Path("limit") limit: Int = 20,
    ): ItunesChartResponse

    @GET("lookup")
    suspend fun lookup(
        @Query("id") ids: String,
        @Query("country") country: String = "KR",
    ): ItunesSearchResponse
}