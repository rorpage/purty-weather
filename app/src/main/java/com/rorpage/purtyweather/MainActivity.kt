package com.rorpage.purtyweather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rorpage.purtyweather.managers.ServiceManager
import com.rorpage.purtyweather.ui.PurtyWeatherApp
import com.rorpage.purtyweather.util.WeatherUpdateScheduler
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           PurtyWeatherApp()
        }
        checkPermissions()
        ServiceManager.startUpdateWeatherService(applicationContext)
        WeatherUpdateScheduler.scheduleJob(applicationContext)
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

