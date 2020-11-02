package com.rorpage.purtyweather.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import timber.log.Timber

class WeatherIconsUtil(val context: Context) {

    fun getIcon(iconFileName: String): Drawable? {
        Timber.v("Icon Name $iconFileName")
        return ContextCompat.getDrawable(context, context.resources.getIdentifier("wi_$iconFileName", "drawable", context.packageName))
    }

}