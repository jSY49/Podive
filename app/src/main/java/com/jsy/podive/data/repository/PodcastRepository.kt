package com.jsy.podive.data.repository

import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.model.PodcastDetail

interface PodcastRepository {
    suspend fun searchPodcast(term: String) : Result<List<Podcast>>
    suspend fun getTopPodcasts(genreId: Int? = null, limit: Int = 20): Result<List<Podcast>>
    suspend fun lookupPodcasts(ids: List<Long>) : Result<List<Podcast>>
    suspend fun getPodcastDetail(id: Long, desc: String = "", episodeLimit: Int = 20): Result<PodcastDetail>
}
