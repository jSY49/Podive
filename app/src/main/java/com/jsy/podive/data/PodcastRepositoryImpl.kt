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

    // 차트(ID, 설명) -> lookup(상세 정보) 순서로 호출해서 합친다
    override suspend fun getTopPodcasts(limit: Int): Result<List<Podcast>> {
        return runCatching {
            val entries = api.getTopPodcasts(limit = limit).feed.entry

            // 차트 순위 순서의 ID 목록
            val ids = entries.mapNotNull { it.id.attributes.id.toLongOrNull() }
            if (ids.isEmpty()) return@runCatching emptyList()

            // ID -> 설명 (설명은 차트에만 있어서 lookup 결과에 붙여준다)
            val descriptions = entries.mapNotNull { entry ->
                val id = entry.id.attributes.id.toLongOrNull() ?: return@mapNotNull null
                id to entry.summary?.label.orEmpty()
            }.toMap()

            api.lookup(ids.joinToString(","))
                .toPodcasts(descriptions)
                .sortedBy { ids.indexOf(it.id) }    // lookup은 순서를 보장하지 않아서 차트 순서로 정렬
        }
    }

    // ID 목록으로 상세 정보를 조회한다 (추천 목록처럼 직접 정한 ID에 사용)
    override suspend fun lookupPodcasts(ids: List<Long>): Result<List<Podcast>> {
        return runCatching {
            if (ids.isEmpty()) return@runCatching emptyList()

            api.lookup(ids.joinToString(","))
                .toPodcasts()
                .sortedBy { ids.indexOf(it.id) }    // 요청한 ID 순서로 정렬
        }
    }

}