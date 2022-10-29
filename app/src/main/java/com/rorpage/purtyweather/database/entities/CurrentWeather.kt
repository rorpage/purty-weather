package com.rorpage.purtyweather.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentWeather(
        @PrimaryKey()
        val id: Int,
        val temperature: Double,
        val feelsLike: Double,
        val dt: Long,
        var sunrise: Long,
        var sunset: Long,
        var pressure: Int,
        var humidity: Int,
        var dewPoint: Double,
        var uvi: Double,
        var clouds: Int,
        var visibility: Int,
        var windSpeed: Double,
        var windDeg: Int
)