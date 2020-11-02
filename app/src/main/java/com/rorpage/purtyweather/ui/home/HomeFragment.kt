package com.rorpage.purtyweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.daos.CurrentWeatherDAO
import com.rorpage.purtyweather.util.WeatherIconsUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject lateinit var currentWeatherDAO: CurrentWeatherDAO
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val dateTextView = root.findViewById<TextView>(R.id.todays_date)
        val temperatureTextView = root.findViewById<TextView>(R.id.temperature)
        val feelsLikeTemperatureTextView = root.findViewById<AppCompatTextView>(R.id.feelsLikeTemperature)
        val currentWeatherIcon = root.findViewById<AppCompatImageView>(R.id.currentWeatherIcon)
        val description = root.findViewById<AppCompatTextView>(R.id.description)

        homeViewModel.dateLiveData.observe(viewLifecycleOwner, { s -> dateTextView.text = s })
        homeViewModel.temperatureLiveData.observe(viewLifecycleOwner, { s -> temperatureTextView.text = s })

        // TODO: We should eventually move database calls into a repository and then call into it inside the HomeViewModel
        currentWeatherDAO.getCurrentWeatherWithWeatherList()
                .observe(viewLifecycleOwner, { currentWeatherWithWeatherList ->
                    temperatureTextView.text = if (currentWeatherWithWeatherList != null) {
                        getString(R.string.temperature, currentWeatherWithWeatherList.currentWeather.temperature.roundToInt())
                    } else { "-°" }

                    feelsLikeTemperatureTextView.text = if (currentWeatherWithWeatherList != null) {
                        getString(R.string.temperature, currentWeatherWithWeatherList.currentWeather.feelsLike.roundToInt())
                    } else { "-°" }

                    if (currentWeatherWithWeatherList != null && currentWeatherWithWeatherList.weatherList.isNotEmpty()) {
                        currentWeatherIcon.setImageDrawable(context?.let { WeatherIconsUtil(it).getIcon(currentWeatherWithWeatherList.weatherList.first().icon) })
                        description.text = currentWeatherWithWeatherList.weatherList.first().description
                    }
                })

        return root
    }

    override fun onDestroyView() {
        homeViewModel.dateLiveData.removeObservers(viewLifecycleOwner)
        homeViewModel.temperatureLiveData.removeObservers(viewLifecycleOwner)
        currentWeatherDAO.getCurrentWeather().removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }
}