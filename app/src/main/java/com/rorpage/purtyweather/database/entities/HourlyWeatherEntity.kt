package com.rorpage.purtyweather.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HourlyWeatherEntity(
    @PrimaryKey
    val entityId: Int,
    val hourlyWeatherId: Int,
    val iconId: Int,
    val main: String,
    val description: String,
    val icon: String
)
