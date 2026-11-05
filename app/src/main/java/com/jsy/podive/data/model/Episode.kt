package com.jsy.podive.data.model

data class Episode(
    val id: Long,
    val title: String,
    val description: String,
    val releaseDate: String,    // ISO-8601 (예: 2026-10-31T09:00:00Z)
    val durationMillis: Long,
    val audioUrl: String,       // 뮤직 플레이어에서 재생할 주소
)

// 팟캐스트 상세 화면에서 한 번에 쓰는 묶음
data class PodcastDetail(
    val podcast: Podcast,
    val episodes: List<Episode>,
)
