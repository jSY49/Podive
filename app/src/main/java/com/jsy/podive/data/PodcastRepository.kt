package com.jsy.podive.data

import com.jsy.podive.data.model.Podcast

interface PodcastRepository {
    suspend fun searchPodcast(term: String) : Result<List<Podcast>>
}