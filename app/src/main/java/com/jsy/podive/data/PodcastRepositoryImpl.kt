package com.jsy.podive.data

import com.jsy.podive.data.mapper.toPodcasts
import com.jsy.podive.data.model.Podcast
import com.jsy.podive.data.remote.ItunesApi

// 인터페이스 구현체, 실제 api 호출 하는 곳
class PodcastRepositoryImpl(
    private val api : ItunesApi
) : PodcastRepository {

    override suspend fun searchPodcast(term: String): Result<List<Podcast>> {
        return runCatching {    //runCatching = try-catch , 예외가 나면 Result에 담아줌
            api.searchPodcasts(term).toPodcasts()
        }
    }

}