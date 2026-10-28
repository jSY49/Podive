package com.jsy.podive.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsy.podive.ui.explore.ExploreScreen
import com.jsy.podive.ui.home.HomeScreen
import com.jsy.podive.ui.library.LibraryScreen
import com.jsy.podive.ui.navigation.BottomTabs
import com.jsy.podive.ui.navigation.Routes

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val tabs = BottomTabs.entries
    var currentTab by remember { mutableStateOf<BottomTabs>(BottomTabs.HomeTab) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        icon = {
                            Image(
                                painter = painterResource(tab.icon),
                                contentDescription = stringResource(tab.desc),
                                colorFilter = ColorFilter.tint(
                                    if (currentTab == tab) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                            )
                        },
                        label = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(tab.label),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (currentTab == tab) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(4.dp)
                                        .background(
                                            color = if (currentTab == tab) MaterialTheme.colorScheme.primary
                                            else Color.Transparent,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                BottomTabs.HomeTab -> HomeScreen()
                BottomTabs.ExploreTab -> ExploreScreen()
                BottomTabs.LibraryTab -> LibraryScreen()
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
private fun MainScreenPreview() {
    MainScreen()
}