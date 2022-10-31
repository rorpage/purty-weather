package com.rorpage.purtyweather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rorpage.purtyweather.database.daos.CurrentWeatherDAO
import com.rorpage.purtyweather.database.daos.HourlyDAO
import com.rorpage.purtyweather.database.daos.HourlyWeatherDAO
import com.rorpage.purtyweather.database.daos.WeatherDAO
import com.rorpage.purtyweather.database.entities.CurrentWeather
import com.rorpage.purtyweather.database.entities.HourlyEntity
import com.rorpage.purtyweather.database.entities.HourlyWeatherEntity
import com.rorpage.purtyweather.database.entities.WeatherEntity

@Database(entities = [CurrentWeather::class, WeatherEntity::class, HourlyEntity::class, HourlyWeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentWeatherDAO(): CurrentWeatherDAO
    abstract fun weatherDAO(): WeatherDAO
    abstract fun hourlyDAO(): HourlyDAO
    abstract fun hourlyWeatherDAO(): HourlyWeatherDAO
}
