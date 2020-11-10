package com.rorpage.purtyweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.entities.HourlyWeatherWithWeatherList
import com.rorpage.purtyweather.util.WeatherIconsUtil
import timber.log.Timber

class HourlyForecastAdapter(private val hourlyWeatherLists: ArrayList<List<HourlyWeatherWithWeatherList>>) : RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_group, parent, false)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: HourlyForecastAdapter.ViewHolder, position: Int) {
        holder.bindItems(hourlyWeatherLists[position])
    }

    override fun getItemCount(): Int {
        return hourlyWeatherLists.size
    }

    inner class ViewHolder(itemView: View, val parent: ViewGroup?) : RecyclerView.ViewHolder(itemView) {

        private lateinit var hourlyItem1: ConstraintLayout
        private lateinit var hourlyItem2: ConstraintLayout
        private lateinit var hourlyItem3: ConstraintLayout
        private lateinit var hourlyItem4: ConstraintLayout
        private lateinit var hourlyItem5: ConstraintLayout
        private lateinit var hourlyItem6: ConstraintLayout
        private lateinit var hourlyItem7: ConstraintLayout
        private lateinit var hourlyItem8: ConstraintLayout
        private lateinit var hourlyItem1Icon: ImageView
        private lateinit var hourlyItem2Icon: ImageView
        private lateinit var hourlyItem3Icon: ImageView
        private lateinit var hourlyItem4Icon: ImageView
        private lateinit var hourlyItem5Icon: ImageView
        private lateinit var hourlyItem6Icon: ImageView
        private lateinit var hourlyItem7Icon: ImageView
        private lateinit var hourlyItem8Icon: ImageView

        fun bindItems(item: List<HourlyWeatherWithWeatherList>) {

            Timber.v("item list size ${item.size}")
            // Hourly Element 1
            hourlyItem1 = itemView.findViewById(R.id.hourlyItem1)
            hourlyItem1Icon = hourlyItem1.findViewById(R.id.hourlyImageView)
            hourlyItem1Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[0].hourlyWeatherList[0].icon))

            // Hourly Element 2
            hourlyItem2 = itemView.findViewById(R.id.hourlyItem2)
            hourlyItem2Icon = hourlyItem2.findViewById(R.id.hourlyImageView)
            hourlyItem2Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[1].hourlyWeatherList[0].icon))

            // Hourly Element 3
            hourlyItem3 = itemView.findViewById(R.id.hourlyItem3)
            hourlyItem3Icon = hourlyItem3.findViewById(R.id.hourlyImageView)
            hourlyItem3Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[2].hourlyWeatherList[0].icon))

            // Hourly Element 4
            hourlyItem4 = itemView.findViewById(R.id.hourlyItem4)
            hourlyItem4Icon = hourlyItem4.findViewById(R.id.hourlyImageView)
            hourlyItem4Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[3].hourlyWeatherList[0].icon))

            // Hourly Element 5
            hourlyItem5 = itemView.findViewById(R.id.hourlyItem5)
            hourlyItem5Icon = hourlyItem5.findViewById(R.id.hourlyImageView)
            hourlyItem5Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[4].hourlyWeatherList[0].icon))

            // Hourly Element 6
            hourlyItem6 = itemView.findViewById(R.id.hourlyItem6)
            hourlyItem6Icon = hourlyItem6.findViewById(R.id.hourlyImageView)
            hourlyItem6Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[5].hourlyWeatherList[0].icon))

            // Hourly Element 7
            hourlyItem7 = itemView.findViewById(R.id.hourlyItem7)
            hourlyItem7Icon = hourlyItem7.findViewById(R.id.hourlyImageView)
            hourlyItem7Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[6].hourlyWeatherList[0].icon))

            // Hourly Element 8
            hourlyItem8 = itemView.findViewById(R.id.hourlyItem8)
            hourlyItem8Icon = hourlyItem8.findViewById(R.id.hourlyImageView)
            hourlyItem8Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[7].hourlyWeatherList[0].icon))

        }

    }

}