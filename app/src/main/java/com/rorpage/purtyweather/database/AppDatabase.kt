package com.rorpage.purtyweather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rorpage.purtyweather.database.daos.CurrentTemperatureDAO
import com.rorpage.purtyweather.database.entities.CurrentTemperature
import com.rorpage.purtyweather.models.WeatherInfoUnit

@Database(entities = [CurrentTemperature::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentTemperatureDAO(): CurrentTemperatureDAO
}