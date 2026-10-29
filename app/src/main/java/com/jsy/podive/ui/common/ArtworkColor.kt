package com.jsy.podive.ui.common

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.get
import androidx.core.graphics.scale
import coil3.SingletonImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Bitmap.averageColor(): Color {

    val pixel = 16

    val small = scale(
        pixel,
        pixel
    )
    var r = 0L
    var g = 0L
    var b = 0L
    for (x in 0 until pixel) {
        for (y in 0 until pixel) {
            val p = small[x, y]
            r += android.graphics.Color.red(p)
            g += android.graphics.Color.green(p)
            b += android.graphics.Color.blue(p)
        }
    }

    val size = pixel * pixel
    return Color(
        r.toInt() / size,
        g.toInt() / size,
        b.toInt() / size
    )
}

/**
 * 아트워크 이미지(url)의 평균 색상을 계산해서 돌려준다.
 * 계산이 끝나기 전에는 [default]를 돌려주고, 끝나면 평균색으로 바뀌면서 이 값을 쓰는 UI가 다시 그려진다.
 */
@Composable
fun rememberArtworkColor(url: String, default: Color): Color {

    val context = LocalContext.current
    // url이 바뀌면 이전 색을 버리고 default부터 다시 시작한다
    var color by remember(url) { mutableStateOf(default) }

    // url이 바뀔 때마다 실행되는 코루틴 (suspend 함수인 execute를 쓰려고 필요)
    LaunchedEffect(url) {

        val request = ImageRequest.Builder(context)
            .data(url)
            // 하드웨어 Bitmap은 CPU가 픽셀을 못 읽어서 averageColor()에서 에러가 난다
            .allowHardware(false)
            .build()

        val result = SingletonImageLoader.get(context).execute(request)
        // 실패하면 default 색을 그대로 유지한다
        if (result is SuccessResult) {
            val bitmap = result.image.toBitmap()
            // 픽셀 반복 계산은 메인 스레드를 막지 않도록 백그라운드에서 한다
            color = withContext(Dispatchers.Default) { bitmap.averageColor() }
        }
    }
    return color
}