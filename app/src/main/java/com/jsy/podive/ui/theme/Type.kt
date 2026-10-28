package com.jsy.podive.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jsy.podive.R

private val Pretendard = FontFamily(
    Font(
        R.font.pretendard_light,
        FontWeight.Light
    ),
    Font(
        R.font.pretendard_regular,
        FontWeight.Normal
    ),
    Font(
        R.font.pretendard_semibold,
        FontWeight.SemiBold
    ),
    Font(
        R.font.pretendard_bold,
        FontWeight.Bold
    ),
)

val Typography = Typography(
    // Podive 27 Bold
    headlineLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    // 화면 제목 26 Bold
    headlineMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 31.sp,
        letterSpacing = 0.sp
    ),
    // 섹션 · 카드 제목 19 Bold
    titleLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    // 에피소드 제목 16 SemiBold (최대 2줄은 Text의 maxLines = 2 로 처리)
    titleMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    ),
    // 본문 14 / 22
    bodyMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    //칩
    labelMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.sp
    ),

    // 날짜 · 길이 · 용량 12
    labelSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    ),
)
