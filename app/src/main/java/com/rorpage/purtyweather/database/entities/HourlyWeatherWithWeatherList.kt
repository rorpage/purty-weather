package com.rorpage.purtyweather.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class HourlyWeatherWithWeatherList(
    @Embedded val hourlyEntity: HourlyEntity,
    @Relation(
            parentColumn = "id",
            entityColumn = "hourlyWeatherId"
    )
    val hourlyWeatherList: List<HourlyWeatherEntity>
)