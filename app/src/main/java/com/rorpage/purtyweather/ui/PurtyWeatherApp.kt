package com.rorpage.purtyweather.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.ui.home.HomeScreen
import com.rorpage.purtyweather.ui.home.HomeViewModel
import com.rorpage.purtyweather.ui.theme.PurtyBlue
import com.rorpage.purtyweather.ui.theme.PurtyWeatherTheme

object MainDestinations {
    const val HOME_ROUTE = "home"
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen(MainDestinations.HOME_ROUTE, R.string.title_home)
}

val screens = listOf(Screen.Home)

@ExperimentalPagerApi
@Composable
fun PurtyWeatherApp() {
    PurtyWeatherTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    screens.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(Icons.Filled.Home, contentDescription = null, tint = PurtyBlue) },
                            label = { Text(stringResource(id = screen.resourceId), color = PurtyBlue) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.background(Color.White)
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
                composable(Screen.Home.route) {
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    HomeScreen(homeViewModel)
                }
            }
        }
    }
}
