package com.jsy.podive.ui.navigation

import android.app.ActionBar
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jsy.podive.R

sealed class BottomTabs(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @StringRes val desc: Int,
) {

    object HomeTab : BottomTabs(
        label = R.string.nav_home,
        icon = R.drawable.ic_home,
        desc = R.string.nav_home
    )

    object ExploreTab : BottomTabs(
        label = R.string.nav_explore,
        icon = R.drawable.ic_explore,
        desc = R.string.nav_explore
    )

    object LibraryTab : BottomTabs(
        label = R.string.nav_lib,
        icon = R.drawable.ic_library_music,
        desc = R.string.nav_lib
    )

    companion object {
        val entries = listOf(
            HomeTab,
            ExploreTab,
            LibraryTab
        )
    }
}