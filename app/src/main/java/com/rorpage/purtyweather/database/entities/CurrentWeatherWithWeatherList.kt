package com.rorpage.purtyweather.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CurrentWeatherWithWeatherList(
    @Embedded val currentWeather: CurrentWeather,
    @Relation(
        parentColumn = "id",
        entityColumn = "currentWeatherId"
    )
    val weatherList: List<WeatherEntity>
)
