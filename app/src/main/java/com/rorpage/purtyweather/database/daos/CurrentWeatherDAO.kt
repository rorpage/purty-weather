package com.rorpage.purtyweather.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rorpage.purtyweather.database.entities.CurrentWeather
import com.rorpage.purtyweather.database.entities.CurrentWeatherWithWeatherList

@Dao
interface CurrentWeatherDAO {

    @Query("SELECT * FROM currentweather WHERE id = 1")
    fun getCurrentWeather(): LiveData<CurrentWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentTemperature( currentTemperature: CurrentWeather)

    @Transaction
    @Query("SELECT * FROM CurrentWeather WHERE id = 1")
    fun getCurrentWeatherWithWeatherList(): LiveData<CurrentWeatherWithWeatherList>
}