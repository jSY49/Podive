package com.jsy.podive.data.remote.dto

import kotlinx.serialization.Serializable

// lookup?entity=podcastEpisode 응답: results[0] 은 팟캐스트 자신, 나머지는 에피소드
// (genres 는 팟캐스트/에피소드에서 타입이 달라서 일부러 선언하지 않는다)
@Serializable
data class ItunesEpisodeResponse(
    val resultCount: Int,
    val results: List<ItunesEpisodeDto>,
)

@Serializable
data class ItunesEpisodeDto(
    val kind: String? = null,   // 팟캐스트: "podcast", 에피소드: "podcast-episode"
    val trackId: Long? = null,
    val trackName: String? = null,
    val collectionId: Long? = null,
    val collectionName: String? = null,
    val artistName: String? = null,
    val feedUrl: String? = null,
    val artworkUrl600: String? = null,
    val primaryGenreName: String? = null,
    val trackCount: Int? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val releaseDate: String? = null,
    val trackTimeMillis: Long? = null,
    val episodeUrl: String? = null,
)
