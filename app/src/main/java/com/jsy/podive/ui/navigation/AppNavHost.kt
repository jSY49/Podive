package com.jsy.podive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jsy.podive.ui.main.MainScreen
import com.jsy.podive.ui.podcast.PodcastDetailScreen
import com.jsy.podive.ui.podcastlist.PodcastListScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {

        composable(Routes.MAIN) {
            MainScreen(
                onPodcastClick = { podcast ->
                    navController.navigate(Routes.podcastDetail(podcast.id, podcast.desc))
                },
                onViewTotalClick = { navController.navigate(Routes.PODCAST_LIST) }
            )
        }

        composable(Routes.PODCAST_LIST) {
            PodcastListScreen(
                onBack = { navController.popBackStack() },
                onPodcastClick = { podcast ->
                    navController.navigate(Routes.podcastDetail(podcast.id, podcast.desc))
                }
            )
        }

        composable(
            route = Routes.PODCAST_DETAIL,
            arguments = listOf(
                navArgument(Routes.ARG_ID) { type = NavType.LongType },
                navArgument(Routes.ARG_DESC) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            // id, desc 는 ViewModel 이 SavedStateHandle 로 꺼내 쓴다
            PodcastDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
