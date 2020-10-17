package com.rorpage.purtyweather.di

import android.content.Context
import androidx.room.Room
import com.rorpage.purtyweather.database.AppDatabase
import com.rorpage.purtyweather.database.daos.CurrentTemperatureDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideCurrentTemperatureDAO(appDatabase: AppDatabase): CurrentTemperatureDAO {
        return appDatabase.currentTemperatureDAO()
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