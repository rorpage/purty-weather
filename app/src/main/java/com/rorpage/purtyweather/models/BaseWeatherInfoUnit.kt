package com.rorpage.purtyweather.models

import com.google.gson.annotations.SerializedName

open class BaseWeatherInfoUnit {
    var dt: Long = 0
    var sunrise: Long = 0
    var sunset: Long = 0
    var pressure = 0
    var humidity = 0
    @SerializedName("dew_point")
    var dewPoint = 0.0
    var uvi = 0.0
    var clouds = 0
    var visibility = 0
    @SerializedName("wind_speed")
    var windSpeed = 0.0
    @SerializedName("wind_deg")
    var windDeg = 0
    var weather: List<Weather>? = null
}