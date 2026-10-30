package com.jsy.podive.data

import com.jsy.podive.data.model.Podcast

interface PodcastRepository {
    suspend fun searchPodcast(term: String) : Result<List<Podcast>>
    suspend fun getTopPodcasts(genreId: Int? = null, limit: Int = 20): Result<List<Podcast>>
    suspend fun lookupPodcasts(ids: List<Long>) : Result<List<Podcast>>
}