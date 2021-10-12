package com.rorpage.purtyweather.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rorpage.purtyweather.ui.home.HomeScreen


object MainDestinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun PurtyWeatherNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier.fillMaxSize(),
    startDestination: String
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(MainDestinations.HOME_ROUTE) { backStackEntry ->
            HomeScreen()
        }
    }
}


/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {

}