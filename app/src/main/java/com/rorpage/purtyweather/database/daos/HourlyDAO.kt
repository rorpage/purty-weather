package com.rorpage.purtyweather.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rorpage.purtyweather.database.entities.HourlyEntity
import com.rorpage.purtyweather.database.entities.HourlyWeatherWithWeatherList

@Dao
interface HourlyDAO {

    @Query("SELECT * FROM hourlyentity")
    fun getHourly(): LiveData<HourlyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHourly(hourlyEntity: HourlyEntity)

    @Transaction
    @Query("SELECT * FROM HourlyEntity")
    fun getHourlyWeatherWithWeatherList(): LiveData<List<HourlyWeatherWithWeatherList>>

}