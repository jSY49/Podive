package com.jsy.podive.data.mapper

import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.remote.dto.ItunesSearchResponse

fun ItunesSearchResponse.toPodcasts(
    descriptions: Map<Long, String> = emptyMap(),
): List<Podcast> {

    return results.mapNotNull { dto ->
        Podcast(
            id = dto.collectionId,
            title = dto.collectionName ?: return@mapNotNull null,
            author = dto.artistName.orEmpty(),
            feedUrl = dto.feedUrl ?: return@mapNotNull null,
            artworkUrl = dto.artworkUrl600.orEmpty(),
            episodeCount = dto.trackCount ?: 0,
            genre = dto.primaryGenreName.orEmpty(),
            desc = descriptions[dto.collectionId].orEmpty(),
        )
    }
}
