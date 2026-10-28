package com.jsy.podive.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Navy,              // 메인 버튼, 선택된 칩, 재생 진행 바
    onPrimary = White,
    secondary = Blue,            // 다운로드 진행률, 상태 라벨, '구독' 글자 버튼
    onSecondary = White,
    secondaryContainer = Sand,   // NavigationBar 선택 표시
    onSecondaryContainer = Navy,
    background = Ivory,
    onBackground = Ink,
    surface = White,
    onSurface = Ink,
    surfaceVariant = Sand,
    onSurfaceVariant = Slate,
    surfaceTint = Color.Transparent,
    surfaceContainerLowest = White,
    surfaceContainerLow = White,     // BottomSheet (재생 설정 시트)
    surfaceContainer = White,        // NavigationBar
    surfaceContainerHigh = White,
    surfaceContainerHighest = Sand,  // 채워진 검색창, 관리 묶음 카드, Switch 꺼짐 트랙
    outline = Line,
    outlineVariant = Line,
    inverseSurface = Navy,           // Snackbar
    inverseOnSurface = White,
    error = Red,
    onError = White,
    errorContainer = ErrorBg,
    onErrorContainer = ErrorText
)

// 파일: app/src/main/java/com/example/app/ui/theme/Theme.kt
private val LightColorScheme = lightColorScheme(
    primary = Navy,              // 메인 버튼, 선택된 칩, 재생 진행 바
    onPrimary = White,
    secondary = Blue,            // 다운로드 진행률, 상태 라벨, '구독' 글자 버튼
    onSecondary = White,
    secondaryContainer = Sand,   // NavigationBar 선택 표시
    onSecondaryContainer = Navy,
    background = Ivory,
    onBackground = Ink,
    surface = White,
    onSurface = Ink,
    surfaceVariant = Sand,
    onSurfaceVariant = Slate,
    surfaceTint = Color.Transparent,
    surfaceContainerLowest = White,
    surfaceContainerLow = White,     // BottomSheet (재생 설정 시트)
    surfaceContainer = White,        // NavigationBar
    surfaceContainerHigh = White,
    surfaceContainerHighest = Sand,  // 채워진 검색창, 관리 묶음 카드, Switch 꺼짐 트랙
    outline = Line,
    outlineVariant = Line,
    inverseSurface = Navy,           // Snackbar
    inverseOnSurface = White,
    error = Red,
    onError = White,
    errorContainer = ErrorBg,
    onErrorContainer = ErrorText
)
@Composable
fun PodiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}