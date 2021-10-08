package com.rorpage.purtyweather.di

import android.content.Context
import androidx.room.Room
import com.rorpage.purtyweather.database.AppDatabase
import com.rorpage.purtyweather.database.daos.CurrentWeatherDAO
import com.rorpage.purtyweather.database.daos.HourlyDAO
import com.rorpage.purtyweather.database.daos.HourlyWeatherDAO
import com.rorpage.purtyweather.database.daos.WeatherDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideCurrentWeatherDAO(appDatabase: AppDatabase): CurrentWeatherDAO {
        return appDatabase.currentWeatherDAO()
    }

    @Provides
    fun provideWeatherDAO(appDatabase: AppDatabase): WeatherDAO {
        return appDatabase.weatherDAO()
    }

    @Provides
    fun provideHourlyDAO(appDatabase: AppDatabase): HourlyDAO {
        return appDatabase.hourlyDAO()
    }

    @Provides
    fun provideHourlyWeatherDAO(appDatabase: AppDatabase): HourlyWeatherDAO {
        return appDatabase.hourlyWeatherDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "com.rorpage.purtyweather.roomdb"
        )
                // TODO: 10/17/20 We will want to add support for backgrounding these, probably with coroutines
                .allowMainThreadQueries()
                .build()
    }
}