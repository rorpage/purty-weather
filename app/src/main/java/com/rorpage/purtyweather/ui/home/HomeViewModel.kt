package com.rorpage.purtyweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rorpage.purtyweather.database.daos.CurrentWeatherDAO
import com.rorpage.purtyweather.database.daos.HourlyDAO
import com.rorpage.purtyweather.database.entities.CurrentWeatherWithWeatherList
import com.rorpage.purtyweather.database.entities.HourlyWeatherWithWeatherList
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val currentWeatherDAO: CurrentWeatherDAO, val hourlyDAO: HourlyDAO) : ViewModel() {

    private val dateText: MutableLiveData<String> = MutableLiveData()
    val dateLiveData: LiveData<String>
        get() = dateText

    private val temperatureText: MutableLiveData<String> = MutableLiveData()
    val temperatureLiveData: LiveData<String>
        get() = temperatureText

    val localDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    val hourlyFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("ha")
        .withLocale(Locale.US)
        .withZone(ZoneId.systemDefault())

    init {
        dateText.value = LocalDate.now().format(localDateFormatter)
        temperatureText.value = "-°"
    }

    fun getCurrentWeather(): LiveData<CurrentWeatherWithWeatherList> {
        return currentWeatherDAO.getCurrentWeatherWithWeatherList()
    }

    fun getHourlyWeather(): LiveData<List<HourlyWeatherWithWeatherList>> {
        return hourlyDAO.getHourlyWeatherWithWeatherList()
    }
}