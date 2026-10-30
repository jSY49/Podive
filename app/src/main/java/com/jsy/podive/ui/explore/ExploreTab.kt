package com.jsy.podive.ui.explore

import androidx.annotation.StringRes
import com.jsy.podive.R

enum class ExploreTab(@StringRes val label: Int) {
    PodcastSearch(R.string.explore_tab_podcast),
    RssUrl(R.string.explore_tab_rss),
}