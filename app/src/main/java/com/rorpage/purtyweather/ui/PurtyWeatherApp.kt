package com.rorpage.purtyweather.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.rorpage.purtyweather.ui.theme.PurtyWeatherTheme

@Composable
fun PurtyWeatherApp() {
    PurtyWeatherTheme {

        Scaffold {
            PurtyWeatherNavGraph(
                startDestination = MainDestinations.HOME_ROUTE
            )
        }
    }
}