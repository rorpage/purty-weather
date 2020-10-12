package com.rorpage.purtyweather.models

class WeatherInfoUnit : BaseWeatherInfoUnit() {
    @JvmField
    var temp = 0.0

    // TODO: 10/9/20 figure out how to make the model name different than the JSON name (feelsLike)
    @JvmField
    var feels_like = 0.0
}