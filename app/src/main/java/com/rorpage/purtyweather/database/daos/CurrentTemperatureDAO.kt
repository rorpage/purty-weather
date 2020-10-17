package com.rorpage.purtyweather.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rorpage.purtyweather.database.entities.CurrentTemperature

@Dao
interface CurrentTemperatureDAO {

    @Query("SELECT * FROM currenttemperature WHERE id = 1")
    fun getCurrentTemperature(): LiveData<CurrentTemperature>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentTemperature( currentTemperature: CurrentTemperature)
}