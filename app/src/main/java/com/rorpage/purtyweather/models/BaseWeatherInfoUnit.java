package com.rorpage.purtyweather.models;

import java.util.List;

public class BaseWeatherInfoUnit {
    public long dt;
    public long sunrise;
    public long sunset;
    public int pressure;
    public int humidity;
    public double dew_point;
    public double uvi;
    public int clouds;
    public int visibility;
    public double wind_speed;
    public int wind_deg;

    public List<Weather> weather;
}
