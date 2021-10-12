package com.rorpage.purtyweather.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.rorpage.purtyweather.ComposeActivity
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.adapter.HourlyForecastAdapter
import com.rorpage.purtyweather.database.daos.CurrentWeatherDAO
import com.rorpage.purtyweather.database.daos.HourlyDAO
import com.rorpage.purtyweather.database.entities.HourlyWeatherWithWeatherList
import com.rorpage.purtyweather.util.WeatherIconsUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.buttonComposeActivity
import kotlinx.android.synthetic.main.fragment_home.view.currentWeatherIcon
import kotlinx.android.synthetic.main.fragment_home.view.description
import kotlinx.android.synthetic.main.fragment_home.view.feelsLikeTemperature
import kotlinx.android.synthetic.main.fragment_home.view.hourlyTabLayout
import kotlinx.android.synthetic.main.fragment_home.view.hourlyViewPager
import kotlinx.android.synthetic.main.fragment_home.view.temperature
import kotlinx.android.synthetic.main.fragment_home.view.todays_date
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject lateinit var currentWeatherDAO: CurrentWeatherDAO
    @Inject lateinit var hourlyDAO: HourlyDAO
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.dateLiveData.observe(viewLifecycleOwner, { s -> root.todays_date.text = s })
        homeViewModel.temperatureLiveData.observe(viewLifecycleOwner, { s -> root.temperature.text = s })

        // TODO: We should eventually move database calls into a repository and then call into it inside the HomeViewModel
        currentWeatherDAO.getCurrentWeatherWithWeatherList()
                .observe(viewLifecycleOwner, { currentWeatherWithWeatherList ->
                    root.temperature.text = if (currentWeatherWithWeatherList != null) {
                        getString(R.string.temperature, currentWeatherWithWeatherList.currentWeather.temperature.roundToInt())
                    } else { "-°" }

                    root.feelsLikeTemperature.text = if (currentWeatherWithWeatherList != null) {
                        getString(R.string.temperature, currentWeatherWithWeatherList.currentWeather.feelsLike.roundToInt())
                    } else { "-°" }

                    if (currentWeatherWithWeatherList != null && currentWeatherWithWeatherList.weatherList.isNotEmpty()) {
                        root.currentWeatherIcon.setImageDrawable(context?.let { WeatherIconsUtil(it).getIcon(currentWeatherWithWeatherList.weatherList.first().icon) })
                        root.description.text = currentWeatherWithWeatherList.weatherList.first().description
                    }
                })

        hourlyDAO.getHourlyWeatherWithWeatherList()
                .observe(viewLifecycleOwner, { hourlyWeatherList ->

                    Timber.v("List Size: ${hourlyWeatherList.size}" )
                    if (hourlyWeatherList.size >= 16) {
                        val reducedHourlyWeatherList = ArrayList<List<HourlyWeatherWithWeatherList>>()

                        reducedHourlyWeatherList.add(hourlyWeatherList.subList(0, 8))
                        reducedHourlyWeatherList.add(hourlyWeatherList.subList(8, 16))

                        root.hourlyViewPager.adapter = HourlyForecastAdapter(reducedHourlyWeatherList)
                        TabLayoutMediator(root.hourlyTabLayout, root.hourlyViewPager) { tab, position ->
                        }.attach()
                    }
                })

        root.buttonComposeActivity.setOnClickListener {
            val intent = Intent(this.context, ComposeActivity::class.java)

            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        homeViewModel.dateLiveData.removeObservers(viewLifecycleOwner)
        homeViewModel.temperatureLiveData.removeObservers(viewLifecycleOwner)
        currentWeatherDAO.getCurrentWeather().removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }
}