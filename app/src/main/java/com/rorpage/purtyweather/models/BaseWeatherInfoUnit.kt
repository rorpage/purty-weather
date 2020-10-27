package com.rorpage.purtyweather.models

open class BaseWeatherInfoUnit {
    var dt: Long = 0
    var sunrise: Long = 0
    var sunset: Long = 0
    var pressure = 0
    var humidity = 0
    var dew_point = 0.0
    var uvi = 0.0
    var clouds = 0
    var visibility = 0
    var wind_speed = 0.0
    var wind_deg = 0
    var weather: List<Weather>? = null
}