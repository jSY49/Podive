package com.jsy.podive.ui.podcast

import android.content.Context
import androidx.core.text.HtmlCompat
import com.jsy.podive.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("M월 d일")

// "2026-10-31T09:00:00Z" -> "10월 31일" (파싱 실패하면 빈 문자열)
fun formatReleaseDate(iso: String): String = runCatching {
    Instant.parse(iso).atZone(ZoneId.systemDefault()).format(dateFormatter)
}.getOrDefault("")

// 밀리초 -> "24분" / "1시간 5분"
fun formatDuration(context: Context, millis: Long): String {
    val totalMinutes = (millis / 60_000).toInt()
    if (totalMinutes <= 0) return ""
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return if (hours == 0) context.getString(R.string.duration_minutes, minutes)
    else context.getString(R.string.duration_hours_minutes, hours, minutes)
}

// 에피소드 설명은 HTML 태그가 섞여 있어서 글자만 남긴다
fun String.stripHtml(): String =
    HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString().trim()
