package com.rorpage.purtyweather.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rorpage.purtyweather.database.entities.HourlyWeatherEntity

@Dao
interface HourlyWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHourlyWeather(vararg hourlyWeatherEntity: HourlyWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHourlyWeatherList(hourlyWeatherList: List<HourlyWeatherEntity>)

    @Query("SELECT * FROM HourlyWeatherEntity")
    fun loadAllHourlyWeather(): List<HourlyWeatherEntity>

    @Query("DELETE FROM HourlyWeatherEntity")
    fun deleteAll()
}