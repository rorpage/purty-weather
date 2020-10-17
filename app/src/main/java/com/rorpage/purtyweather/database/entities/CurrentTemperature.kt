package com.rorpage.purtyweather.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentTemperature(
        @PrimaryKey()
        val id: Int,
        val temperature: Double,
        val feelsLike: Double
) {

}