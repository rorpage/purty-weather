package com.rorpage.purtyweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.entities.HourlyWeatherEntity

class HourlyForecastAdapter(private val hourlyWeatherLists: List<List<HourlyWeatherEntity>>) : RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item, parent, false)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: HourlyForecastAdapter.ViewHolder, position: Int) {
        holder.bindItems(hourlyWeatherLists[position])
    }

    override fun getItemCount(): Int {
        return hourlyWeatherLists.size
    }

    inner class ViewHolder(itemView: View, val parent: ViewGroup?) : RecyclerView.ViewHolder(itemView) {

        private lateinit var textView: AppCompatTextView

        fun bindItems(item: List<HourlyWeatherEntity>) {
            textView = itemView.findViewById(R.id.itemText)
        }

    }

}