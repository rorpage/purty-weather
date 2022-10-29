package com.rorpage.purtyweather.models

import com.google.gson.annotations.SerializedName

class WeatherInfoUnit : BaseWeatherInfoUnit() {
    var temp = 0.0

    @SerializedName("feels_like")
    var feelsLike = 0.0
}
