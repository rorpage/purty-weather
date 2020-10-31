package com.rorpage.purtyweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.daos.CurrentTemperatureDAO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject lateinit var currentTemperatureDAO: CurrentTemperatureDAO
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val dateTextView = root.findViewById<TextView>(R.id.todays_date)
        val temperatureTextView = root.findViewById<TextView>(R.id.temperature)
        val feelsLikeTemperatureTextView = root.findViewById<AppCompatTextView>(R.id.feelsLikeTemperature)
        homeViewModel.dateLiveData.observe(viewLifecycleOwner, { s -> dateTextView.text = s })
        homeViewModel.temperatureLiveData.observe(viewLifecycleOwner, { s -> temperatureTextView.text = s })

        currentTemperatureDAO.getCurrentTemperature()
                .observe(viewLifecycleOwner, { currentTemperature ->
                    temperatureTextView.text = if (currentTemperature != null) {
                        getString(R.string.temperature, currentTemperature.temperature.roundToInt())
                    } else { "-°" }

                    feelsLikeTemperatureTextView.text = if (currentTemperature != null) {
                        getString(R.string.temperature, currentTemperature.feelsLike.roundToInt())
                    } else { "-°" }
                })

        return root
    }

    override fun onDestroyView() {
        homeViewModel.dateLiveData.removeObservers(viewLifecycleOwner)
        homeViewModel.temperatureLiveData.removeObservers(viewLifecycleOwner)
        currentTemperatureDAO.getCurrentTemperature().removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }
}