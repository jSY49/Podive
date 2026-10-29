package com.jsy.podive.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItunesSearchResponse(
    val resultCount: Int,
    val results: List<ItunesPodcastDto>,
)

@Serializable
data class ItunesPodcastDto(
    val collectionId: Long,
    val collectionName: String? = null,
    val artistName: String? = null,
    val feedUrl: String? = null,
    val artworkUrl600: String? = null,
    val trackCount: Int? = null,
    val primaryGenreName: String? = null,
)