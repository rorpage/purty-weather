package com.rorpage.purtyweather.models

class WeatherInfoUnit : BaseWeatherInfoUnit() {
    var temp = 0.0

    // TODO: 10/9/20 figure out how to make the model name different than the JSON name (feelsLike) [THIS WILL HAPPEN WITH SWITCH TO RETROFIT AND LIKELY MOSHI]
    var feels_like = 0.0
}