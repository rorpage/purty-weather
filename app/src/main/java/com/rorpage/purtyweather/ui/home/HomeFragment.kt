package com.rorpage.purtyweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rorpage.purtyweather.R

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val dateTextView = root.findViewById<TextView>(R.id.todays_date)
        val temperatureTextView = root.findViewById<TextView>(R.id.temperature)
        homeViewModel.dateLiveData.observe(viewLifecycleOwner, { s -> dateTextView.text = s })
        homeViewModel.temperatureLiveData.observe(viewLifecycleOwner, { s -> temperatureTextView.text = s })
        return root
    }
}