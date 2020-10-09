package com.rorpage.purtyweather.models

class WeatherResponse {
    // these annotations can be removed if entire project is converted to Kotlin
    @JvmField
    var current: WeatherInfoUnit? = null
    var hourly: List<WeatherInfoUnit>? = null
    @JvmField
    var daily: List<DailyWeatherInfoUnit>? = null
}