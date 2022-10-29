package com.rorpage.purtyweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.entities.HourlyWeatherWithWeatherList
import com.rorpage.purtyweather.util.WeatherIconsUtil
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
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
        private lateinit var hourlyItem1Text: AppCompatTextView
        private lateinit var hourlyItem2Text: AppCompatTextView
        private lateinit var hourlyItem3Text: AppCompatTextView
        private lateinit var hourlyItem4Text: AppCompatTextView
        private lateinit var hourlyItem5Text: AppCompatTextView
        private lateinit var hourlyItem6Text: AppCompatTextView
        private lateinit var hourlyItem7Text: AppCompatTextView
        private lateinit var hourlyItem8Text: AppCompatTextView
        private lateinit var temp1Text: AppCompatTextView
        private lateinit var temp2Text: AppCompatTextView
        private lateinit var temp3Text: AppCompatTextView
        private lateinit var temp4Text: AppCompatTextView
        private lateinit var temp5Text: AppCompatTextView
        private lateinit var temp6Text: AppCompatTextView
        private lateinit var temp7Text: AppCompatTextView
        private lateinit var temp8Text: AppCompatTextView

        fun bindItems(item: List<HourlyWeatherWithWeatherList>) {

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("ha")
                    .withLocale(Locale.US)
                    .withZone(ZoneId.systemDefault())

            Timber.v("item list size ${item.size}")
            // Hourly Element 1
            hourlyItem1 = itemView.findViewById(R.id.hourlyItem1)
            hourlyItem1Icon = hourlyItem1.findViewById(R.id.hourlyImageView)
            hourlyItem1Text = hourlyItem1.findViewById(R.id.hourText)
            temp1Text = hourlyItem1.findViewById(R.id.tempText)
            hourlyItem1Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[0].hourlyWeatherList[0].icon))
            hourlyItem1Text.text = formatter.format(Instant.ofEpochSecond(item[0].hourlyEntity.dt))
            temp1Text.text = itemView.context.getString(R.string.temperature, item[0].hourlyEntity.temperature.toInt())

            // Hourly Element 2
            hourlyItem2 = itemView.findViewById(R.id.hourlyItem2)
            hourlyItem2Icon = hourlyItem2.findViewById(R.id.hourlyImageView)
            hourlyItem2Text = hourlyItem2.findViewById(R.id.hourText)
            temp2Text = hourlyItem2.findViewById(R.id.tempText)
            hourlyItem2Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[1].hourlyWeatherList[0].icon))
            hourlyItem2Text.text = formatter.format(Instant.ofEpochSecond(item[1].hourlyEntity.dt))
            temp2Text.text = itemView.context.getString(R.string.temperature, item[1].hourlyEntity.temperature.toInt())

            // Hourly Element 3
            hourlyItem3 = itemView.findViewById(R.id.hourlyItem3)
            hourlyItem3Icon = hourlyItem3.findViewById(R.id.hourlyImageView)
            hourlyItem3Text = hourlyItem3.findViewById(R.id.hourText)
            temp3Text = hourlyItem3.findViewById(R.id.tempText)
            hourlyItem3Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[2].hourlyWeatherList[0].icon))
            hourlyItem3Text.text = formatter.format(Instant.ofEpochSecond(item[2].hourlyEntity.dt))
            temp3Text.text = itemView.context.getString(R.string.temperature, item[2].hourlyEntity.temperature.toInt())

            // Hourly Element 4
            hourlyItem4 = itemView.findViewById(R.id.hourlyItem4)
            hourlyItem4Icon = hourlyItem4.findViewById(R.id.hourlyImageView)
            hourlyItem4Text = hourlyItem4.findViewById(R.id.hourText)
            temp4Text = hourlyItem4.findViewById(R.id.tempText)
            hourlyItem4Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[3].hourlyWeatherList[0].icon))
            hourlyItem4Text.text = formatter.format(Instant.ofEpochSecond(item[3].hourlyEntity.dt))
            temp4Text.text = itemView.context.getString(R.string.temperature, item[3].hourlyEntity.temperature.toInt())

            // Hourly Element 5
            hourlyItem5 = itemView.findViewById(R.id.hourlyItem5)
            hourlyItem5Icon = hourlyItem5.findViewById(R.id.hourlyImageView)
            hourlyItem5Text = hourlyItem5.findViewById(R.id.hourText)
            temp5Text = hourlyItem5.findViewById(R.id.tempText)
            hourlyItem5Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[4].hourlyWeatherList[0].icon))
            hourlyItem5Text.text = formatter.format(Instant.ofEpochSecond(item[4].hourlyEntity.dt))
            temp5Text.text = itemView.context.getString(R.string.temperature, item[4].hourlyEntity.temperature.toInt())

            // Hourly Element 6
            hourlyItem6 = itemView.findViewById(R.id.hourlyItem6)
            hourlyItem6Icon = hourlyItem6.findViewById(R.id.hourlyImageView)
            hourlyItem6Text = hourlyItem6.findViewById(R.id.hourText)
            temp6Text = hourlyItem6.findViewById(R.id.tempText)
            hourlyItem6Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[5].hourlyWeatherList[0].icon))
            hourlyItem6Text.text = formatter.format(Instant.ofEpochSecond(item[5].hourlyEntity.dt))
            temp6Text.text = itemView.context.getString(R.string.temperature, item[5].hourlyEntity.temperature.toInt())

            // Hourly Element 7
            hourlyItem7 = itemView.findViewById(R.id.hourlyItem7)
            hourlyItem7Icon = hourlyItem7.findViewById(R.id.hourlyImageView)
            hourlyItem7Text = hourlyItem7.findViewById(R.id.hourText)
            temp7Text = hourlyItem7.findViewById(R.id.tempText)
            hourlyItem7Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[6].hourlyWeatherList[0].icon))
            hourlyItem7Text.text = formatter.format(Instant.ofEpochSecond(item[6].hourlyEntity.dt))
            temp7Text.text = itemView.context.getString(R.string.temperature, item[6].hourlyEntity.temperature.toInt())

            // Hourly Element 8
            hourlyItem8 = itemView.findViewById(R.id.hourlyItem8)
            hourlyItem8Icon = hourlyItem8.findViewById(R.id.hourlyImageView)
            hourlyItem8Text = hourlyItem8.findViewById(R.id.hourText)
            temp8Text = hourlyItem8.findViewById(R.id.tempText)
            hourlyItem8Icon.setImageDrawable(WeatherIconsUtil(itemView.context).getIcon(item[7].hourlyWeatherList[0].icon))
            hourlyItem8Text.text = formatter.format(Instant.ofEpochSecond(item[7].hourlyEntity.dt))
            temp8Text.text = itemView.context.getString(R.string.temperature, item[7].hourlyEntity.temperature.toInt())

        }

    }

}