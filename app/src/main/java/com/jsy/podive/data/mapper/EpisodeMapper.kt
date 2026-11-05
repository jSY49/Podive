package com.jsy.podive.data.mapper

import com.jsy.podive.data.model.Episode
import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.model.PodcastDetail
import com.jsy.podive.data.remote.dto.ItunesEpisodeDto
import com.jsy.podive.data.remote.dto.ItunesEpisodeResponse

private const val KIND_PODCAST = "podcast"
private const val KIND_EPISODE = "podcast-episode"

fun ItunesEpisodeResponse.toPodcastDetail(desc: String = ""): PodcastDetail? {
    val podcastDto = results.firstOrNull { it.kind == KIND_PODCAST } ?: return null

    return PodcastDetail(
        podcast = podcastDto.toPodcast(desc) ?: return null,
        episodes = results.filter { it.kind == KIND_EPISODE }.mapNotNull { it.toEpisode() },
    )
}

private fun ItunesEpisodeDto.toPodcast(desc: String): Podcast? {
    return Podcast(
        id = collectionId ?: return null,
        title = collectionName ?: return null,
        author = artistName.orEmpty(),
        feedUrl = feedUrl.orEmpty(),
        artworkUrl = artworkUrl600.orEmpty(),
        episodeCount = trackCount ?: 0,
        genre = primaryGenreName.orEmpty(),
        desc = desc,
    )
}

private fun ItunesEpisodeDto.toEpisode(): Episode? {
    return Episode(
        id = trackId ?: return null,
        title = trackName ?: return null,
        description = description ?: shortDescription.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        durationMillis = trackTimeMillis ?: 0L,
        audioUrl = episodeUrl.orEmpty(),
    )
}
