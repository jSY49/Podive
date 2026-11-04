package com.jsy.podive.ui.common.skeleton

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// BigItemCardView 와 같은 비율(276:208), 같은 모서리
@Composable
fun BigItemCardSkeleton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(276f / 208f)
            .clip(RoundedCornerShape(24.dp))
            .shimmer()
    )
}

@Preview(showBackground = true)
@Composable
private fun BigItemCardSkeletonPreview() {
    BigItemCardSkeleton(Modifier.padding(16.dp))
}
