package com.jsy.podive.data.model

data class Podcast(
    val id: Long,
    val title: String,
    val author: String,
    val feedUrl: String,
    val artworkUrl: String,
    val episodeCount: Int,
    val genre: String,
)
