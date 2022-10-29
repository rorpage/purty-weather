package com.rorpage.purtyweather.models

import com.google.gson.annotations.SerializedName

class DailyWeatherInfoUnit : BaseWeatherInfoUnit() {
    var temp: Temperature? = null

    @SerializedName("feels_like")
    var feelsLike: BaseTemperature? = null
}
