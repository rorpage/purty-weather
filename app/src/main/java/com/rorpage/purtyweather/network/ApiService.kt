package com.rorpage.purtyweather.network

import com.rorpage.purtyweather.BuildConfig
import com.rorpage.purtyweather.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("onecall")
    suspend fun getWeather(@Query("lat") latitude: Double,
        @Query("lon") longtitude: Double,
        @Query("appid") appId: String = BuildConfig.apikey,
        @Query("units") units: String = "imperial"): Response<WeatherResponse>

}