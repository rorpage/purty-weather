package com.rorpage.purtyweather.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.entities.CurrentWeather
import com.rorpage.purtyweather.ui.theme.PurtyWeatherTheme
import com.rorpage.purtyweather.util.WeatherIconsUtil
import java.time.Instant
import java.time.LocalDate
import kotlin.math.roundToInt

@ExperimentalPagerApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val currentWeatherWithWeatherList by homeViewModel.getCurrentWeather().observeAsState()
    val hourlyWeatherWithWeatherList by homeViewModel.getHourlyWeather().observeAsState()

    val currentDate = LocalDate.now().format(homeViewModel.localDateFormatter)

    Column(
        Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth(1f)
                .padding(bottom = 20.dp)
        ) {
            Greeting("Purty Person")
            Text(
                currentDate,
                style = MaterialTheme.typography.h2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        WeatherIconsUtil(LocalContext.current)
                            .getIconId(
                                currentWeatherWithWeatherList?.weatherList?.first()?.icon ?: "moon_new"
                            )
                    ),
                    contentDescription = "weather image",
                    modifier = Modifier.width(100.dp).height(100.dp)
                )
                Text(
                    "${currentTempToDisplay(currentWeatherWithWeatherList?.currentWeather)}°",
                    style = MaterialTheme.typography.h1
                ) // TODO: 10/18/21 make text size not scale https://stackoverflow.com/questions/65893939/how-to-convert-textunit-to-dp-in-jetpack-compose

                Text(currentWeatherWithWeatherList?.weatherList?.first()?.description ?: "-")
            }
            Text("Feels like ${feelsLikeTempToDisplay(currentWeatherWithWeatherList?.currentWeather)}°")
        }

        val pagerState = rememberPagerState()

        if (!hourlyWeatherWithWeatherList.isNullOrEmpty() && hourlyWeatherWithWeatherList?.size!! >= 16) {
            val reducedHourlyWeatherList = arrayListOf(
                hourlyWeatherWithWeatherList?.subList(0, 8),
                hourlyWeatherWithWeatherList?.subList(8, 16)
            )

            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) { page ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    reducedHourlyWeatherList[page]?.forEach { hourlyWeatherItem ->
                        HourlyWeatherColumn(
                            tempText = stringResource(R.string.temperature, hourlyWeatherItem.hourlyEntity.temperature.toInt()),
                            hourText = homeViewModel.hourlyFormatter.format(Instant.ofEpochSecond(hourlyWeatherItem.hourlyEntity.dt)),
                            hourlyImageId = WeatherIconsUtil(LocalContext.current).getIconId(hourlyWeatherItem.hourlyWeatherList[0].icon)
                        )
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun HourlyWeatherColumn(tempText: String, hourText: String, hourlyImageId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(100.dp)
    ) {
        Text(text = tempText)
        Text(text = hourText)
        Image(painter = painterResource(id = hourlyImageId), contentDescription = null)
    }
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

private fun currentTempToDisplay(currentWeather: CurrentWeather?): String {
    return currentWeather?.temperature?.roundToInt()?.toString() ?: "-"
}

private fun feelsLikeTempToDisplay(currentWeather: CurrentWeather?): String {
    return currentWeather?.feelsLike?.roundToInt()?.toString() ?: "-"
}
