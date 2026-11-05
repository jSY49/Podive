package com.jsy.podive.ui.navigation

import android.net.Uri

object Routes {

    const val MAIN = "main"

    // 분야별 인기 전체 보기
    const val PODCAST_LIST = "podcast_list"

    // 팟캐스트 상세 (desc 는 차트에만 있는 설명이라 목록에서 넘겨준다)
    const val ARG_ID = "id"
    const val ARG_DESC = "desc"
    const val PODCAST_DETAIL = "podcast/{$ARG_ID}?$ARG_DESC={$ARG_DESC}"

    fun podcastDetail(id: Long, desc: String): String =
        "podcast/$id?$ARG_DESC=${Uri.encode(desc)}"
}
