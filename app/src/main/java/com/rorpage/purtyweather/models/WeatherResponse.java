package com.rorpage.purtyweather.models;

import java.util.List;

public class WeatherResponse {
    public WeatherInfoUnit current;
    public List<WeatherInfoUnit> hourly;
    public List<DailyWeatherInfoUnit> daily;
}
