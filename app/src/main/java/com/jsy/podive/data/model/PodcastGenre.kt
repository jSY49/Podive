package com.jsy.podive.data.model

import androidx.annotation.StringRes
import com.jsy.podive.R

enum class PodcastGenre(val id: Int, @StringRes val label: Int) {
    Technology(1318, R.string.genre_technology),
    Science(1533, R.string.genre_science),
    Business(1321, R.string.genre_business),
    Comedy(1303, R.string.genre_comedy),
    Education(1304, R.string.genre_education),
    News(1489, R.string.genre_news),
}