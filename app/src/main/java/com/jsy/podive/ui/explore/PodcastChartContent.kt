package com.jsy.podive.ui.explore

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jsy.podive.R

@Composable
fun PodcastChartContent(modifier: Modifier = Modifier) {

    //지금 인기
    Text(
        text = stringResource(R.string.explore_header_title),
        style = MaterialTheme.typography.titleLarge
    )

    //분야별 인기
}

@Preview(
    name = "Phone",
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
private fun PodcastChartContentPreview() {
    PodcastChartContent()
}