package com.jsy.podive.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsy.podive.R
import com.jsy.podive.ui.common.ScreenHeader
import com.jsy.podive.ui.common.SegmentedControl
import com.jsy.podive.ui.explore.components.PodcastRssUrlContent

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {

    var selectedTab by remember { mutableStateOf(ExploreTab.PodcastSearch) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        ScreenHeader(
            title = {
                Text(text = stringResource(R.string.explore_header_title))
            },
        )

        SegmentedControl(
            items = ExploreTab.entries.map { stringResource(it.label) },
            selectedIdx = selectedTab.ordinal,
            onSelected = { selectedTab = ExploreTab.entries[it] }
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            when (selectedTab) {
                ExploreTab.PodcastSearch -> PodcastChartContent(modifier = Modifier.weight(1f))
                ExploreTab.RssUrl -> PodcastRssUrlContent(modifier = Modifier.weight(1f))
            }
        }

    }

}

@Preview(
    name = "Phone",
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Preview(
    name = "Phone_flip",
    widthDp = 360,
    heightDp = 880,
    showBackground = true
)
@Preview(
    name = "Tablet",
    widthDp = 1280,
    heightDp = 800,
    showBackground = true
)
@Preview(
    name = "Foldable",
    widthDp = 673,
    heightDp = 841,
    showBackground = true
)
@Composable
private fun ExploreScreenPreview() {
    ExploreScreen()
}