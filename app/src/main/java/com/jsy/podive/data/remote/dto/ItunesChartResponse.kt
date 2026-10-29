package com.jsy.podive.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItunesChartResponse(val feed: ChartFeed)

@Serializable
data class ChartFeed(val entry: List<ChartEntry> = emptyList())

@Serializable
data class ChartEntry(
    val id: ChartId,
    val summary: ItemSummary? = null,
)

@Serializable
class ChartId(
    val attributes: ChartIdAttributes
)

@Serializable
data class ChartIdAttributes(
    @SerialName("im:id") val id: String,    // 키에 ':'가 있어서 이름을 따로 지정
) {

}

@Serializable
class ItemSummary(
    val label: String
)

