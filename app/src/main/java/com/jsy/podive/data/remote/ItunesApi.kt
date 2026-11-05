package com.jsy.podive.data.remote

import com.jsy.podive.data.remote.dto.ItunesChartResponse
import com.jsy.podive.data.remote.dto.ItunesEpisodeResponse
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

    @GET("{country}/rss/toppodcasts/limit={limit}/genre={genre}/json")
    suspend fun getTopPodcastsByGenre(
        @Path("genre") genre: Int,
        @Path("country") country: String = "kr",
        @Path("limit") limit: Int = 20,
    ): ItunesChartResponse

    // 팟캐스트 1개 + 에피소드 목록 (최신순). limit 최대 200
    // TODO(paging) lookup 은 offset 을 지원하지 않는다. 에피소드 페이징은 limit 을 늘려 다시 받는 방식으로 직접 구현해보기
    @GET("lookup")
    suspend fun lookupEpisodes(
        @Query("id") id: Long,
        @Query("entity") entity: String = "podcastEpisode",
        @Query("limit") limit: Int = 20,
        @Query("country") country: String = "KR",
    ): ItunesEpisodeResponse
}
