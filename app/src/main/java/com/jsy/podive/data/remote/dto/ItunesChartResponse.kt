package com.jsy.podive.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class ItunesChartResponse(val feed: ChartFeed)

// iTunes 는 결과가 1개면 entry 를 배열이 아닌 객체로 내려줘서, 객체면 [객체] 로 감싸서 읽는다
object EntryListSerializer : JsonTransformingSerializer<List<ChartEntry>>(
    ListSerializer(ChartEntry.serializer())
) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element is JsonArray) element else JsonArray(listOf(element))
}

@Serializable
data class ChartFeed(
    @Serializable(with = EntryListSerializer::class)
    val entry: List<ChartEntry> = emptyList()
)

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

