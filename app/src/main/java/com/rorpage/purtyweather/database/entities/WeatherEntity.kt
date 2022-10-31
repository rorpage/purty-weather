package com.rorpage.purtyweather.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey
    val entityId: Int,
    val currentWeatherId: Int = 1,
    val iconId: Int,
    val main: String,
    val description: String,
    val icon: String
)
