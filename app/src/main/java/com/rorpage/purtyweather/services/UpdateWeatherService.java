package com.rorpage.purtyweather.services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rorpage.purtyweather.PurtyWeatherApplication;
import com.rorpage.purtyweather.managers.NotificationManager;
import com.rorpage.purtyweather.models.DailyWeatherInfoUnit;
import com.rorpage.purtyweather.models.WeatherResponse;
import com.rorpage.purtyweather.util.GsonRequest;

import timber.log.Timber;

public class UpdateWeatherService extends BaseService {
    private FusedLocationProviderClient fusedLocationClient;

    private NotificationManager mNotificationManager;
    private PurtyWeatherApplication mPurtyWeatherApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationManager = new NotificationManager(this);
        mPurtyWeatherApplication = (PurtyWeatherApplication) super.getApplication();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);

        startForeground(NotificationManager.NOTIFICATION_ID,
                mNotificationManager.sendNotification("Loading..."));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Timber.e("Location permission not granted");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            getWeather(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    private void getWeather(double latitude, double longitude) {
        Timber.d("getWeather");

        final RequestQueue queue = Volley.newRequestQueue(mPurtyWeatherApplication);
        final String url = String.format(
                "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=%s&units=%s",
                Double.toString(latitude),
                Double.toString(longitude),
                com.rorpage.purtyweather.BuildConfig.OWMAPIKEY,
                "imperial");

        GsonRequest<WeatherResponse> weatherResponseGsonRequest = new GsonRequest<>(
                url, WeatherResponse.class, null, new Response.Listener<WeatherResponse>() {
                    @Override
                    public void onResponse(WeatherResponse weatherResponse) {
                        final double currentTemperature = weatherResponse.current.temp;

                        final String title = String.format("%.0f\u00B0 and %s. Feels like %.0f\u00B0.",
                                currentTemperature,
                                weatherResponse.current.weather.get(0).description,
                                weatherResponse.current.feels_like);

                        final DailyWeatherInfoUnit today = weatherResponse.daily.get(0);
                        final String subtitle = String.format("Today: High %.0f\u00B0, low %.0f\u00B0, %s",
                            today.temp.max,
                            today.temp.min,
                            today.weather.get(0).description);

                        final int iconId = getIconId(currentTemperature);

                        mNotificationManager.sendNotification(title, subtitle, iconId);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Timber.e(error);
                    }
                }
        );

        queue.add(weatherResponseGsonRequest);
    }

    private int getIconId(double temperature) {
        try {
            final String iconFormat = (temperature < 0) ? "tempn%d" : "temp%d";
            final String iconName = String.format(iconFormat, (int) temperature);
            return getIconIdFromResources(iconName, "drawable");
        } catch (Exception e) {
            Timber.e(e);
            return getIconIdFromResources("ic_stat_wb_sunny", "mipmap");
        }
    }

    private int getIconIdFromResources(String name, String defType) {
        return mPurtyWeatherApplication.getResources()
                .getIdentifier(name, defType, mPurtyWeatherApplication.getPackageName());
    }
}
