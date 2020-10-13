package com.rorpage.purtyweather

import android.Manifest
import com.rorpage.purtyweather.managers.ServiceManager.startUpdateWeatherService
import com.rorpage.purtyweather.util.WeatherUpdateScheduler.scheduleJob
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.managers.ServiceManager
import com.rorpage.purtyweather.util.WeatherUpdateScheduler
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import android.os.Build
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNav()
        checkPermissions()
        startUpdateWeatherService(applicationContext)
        scheduleJob(applicationContext)
    }

    private fun setupBottomNav() {
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    private fun checkPermissions() {
        val permissionsNeeded = ArrayList<String>()
        permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionsToRequest = ArrayList<String>()
        for (permission in permissionsNeeded) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            val permissionsArray = permissionsToRequest.toTypedArray()
            ActivityCompat.requestPermissions(this, permissionsArray, 1)
        }
    }
}