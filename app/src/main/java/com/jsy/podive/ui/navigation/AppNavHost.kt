package com.jsy.podive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jsy.podive.ui.explore.ExploreScreen
import com.jsy.podive.ui.home.HomeScreen
import com.jsy.podive.ui.library.LibraryScreen
import com.jsy.podive.ui.main.MainScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {

        composable(Routes.MAIN){
            MainScreen()
        }

    }
}