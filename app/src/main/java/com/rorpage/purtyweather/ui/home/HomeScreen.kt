package com.rorpage.purtyweather.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.rorpage.purtyweather.ui.theme.PurtyWeatherTheme

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val currentWeatherWithWeatherList by homeViewModel.getCurrentWeather().observeAsState()

    Greeting(name = "Purty Person")
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PurtyWeatherTheme {
        Greeting("Purty Person")
    }
}