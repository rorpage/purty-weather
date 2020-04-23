package com.rorpage.purtyweather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rorpage.purtyweather.managers.ServiceManager;
import com.rorpage.purtyweather.util.WeatherUpdateScheduler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNav();

        checkPermissions();

        final PurtyWeatherApplication purtyWeatherApplication =
                (PurtyWeatherApplication) getApplication();

        ServiceManager.StartUpdateWeatherService(purtyWeatherApplication);
        WeatherUpdateScheduler.scheduleJob(purtyWeatherApplication);
    }

    private void setupBottomNav() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionsNeeded = new ArrayList<>();
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

            ArrayList<String> permissionsToRequest = new ArrayList<>();

            for (String permission : permissionsNeeded) {
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    permissionsToRequest.add(permission);
                }
            }

            if (permissionsToRequest.size() > 0) {
                String[] permissionsArray = permissionsToRequest.toArray(new String[permissionsToRequest.size()]);
                ActivityCompat.requestPermissions(this, permissionsArray, 1);
            }
        }
    }
}
