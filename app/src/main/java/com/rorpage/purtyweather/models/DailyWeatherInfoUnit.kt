package com.rorpage.purtyweather.models

class DailyWeatherInfoUnit : BaseWeatherInfoUnit() {
    @JvmField
    var temp: Temperature? = null
    var feels_like: BaseTemperature? = null
}