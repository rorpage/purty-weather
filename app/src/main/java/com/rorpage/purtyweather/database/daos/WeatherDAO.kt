package com.rorpage.purtyweather.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rorpage.purtyweather.database.entities.WeatherEntity

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(vararg weatherList: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherList(weatherList: List<WeatherEntity>)

    @Query("SELECT * FROM WeatherEntity")
    fun loadAllWeather(): List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity")
    fun deleteAll()
}