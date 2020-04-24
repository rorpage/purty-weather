package com.rorpage.purtyweather.services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rorpage.purtyweather.PurtyWeatherApplication;
import com.rorpage.purtyweather.managers.NotificationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final double temperature = getCurrentTemperature(response);
                        final String title = getCurrentWeather(response, temperature);
                        final String subtitle = getTodaysWeather(response);
                        final int iconId = getIconId(temperature);

                        mNotificationManager.sendNotification(title, subtitle, iconId);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private double getCurrentTemperature(JSONObject response) {
        try {
            final JSONObject current = response.getJSONObject("current");
            return current.getDouble("temp");
        } catch (JSONException e) {
            Timber.e(e);
            return 0;
        }
    }

    private String getCurrentWeather(JSONObject response, double temperature) {
        try {
            final JSONObject current = response.getJSONObject("current");
            final JSONObject weather = current.getJSONArray("weather").getJSONObject(0);
            final String conditions = weather.getString("description");
            final double feelsLike = current.getDouble("feels_like");

            return String.format("%.0f\u00B0 and %s. Feels like %.0f\u00B0.", temperature,
                    conditions, feelsLike);
        } catch (JSONException e) {
            Timber.e(e);
            return "Error updating weather";
        }
    }

    private String getTodaysWeather(JSONObject response) {
        try {
            final JSONArray daily = response.getJSONArray("daily");
            final JSONObject today = daily.getJSONObject(0);
            final JSONObject todayWeather = today.getJSONArray("weather").getJSONObject(0);
            final String todayConditions = todayWeather.getString("description");

            final JSONObject todayTemp = today.getJSONObject("temp");
            final double todayTempHigh = todayTemp.getDouble("max");
            final double todayTempLow = todayTemp.getDouble("min");

            return String.format("Today: High %.0f\u00B0, low %.0f\u00B0, %s",
                    todayTempHigh, todayTempLow, todayConditions);
        } catch (JSONException e) {
            Timber.e(e);
            return "Retrying...";
        }
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
