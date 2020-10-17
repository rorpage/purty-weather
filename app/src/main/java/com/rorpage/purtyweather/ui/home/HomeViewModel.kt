package com.rorpage.purtyweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeViewModel : ViewModel() {
    private val dateText: MutableLiveData<String> = MutableLiveData()
    val dateLiveData: LiveData<String>
        get() = dateText

    private val temperatureText: MutableLiveData<String> = MutableLiveData()
    val temperatureLiveData: LiveData<String>
        get() = temperatureText

    private val localDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    init {
        dateText.value = LocalDate.now().format(localDateFormatter)
        temperatureText.value = "-°"
    }
}