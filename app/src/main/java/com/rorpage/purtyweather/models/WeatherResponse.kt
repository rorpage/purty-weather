package com.rorpage.purtyweather.models

class WeatherResponse {
    var current: WeatherInfoUnit? = null
    var hourly: List<WeatherInfoUnit>? = null
    var daily: List<DailyWeatherInfoUnit>? = null
}
