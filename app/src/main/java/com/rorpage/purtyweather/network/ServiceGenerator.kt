package com.rorpage.purtyweather.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    companion object {

        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

        private val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())

        fun <S> createService(serviceClass: Class<S>): S {
            val retrofit: Retrofit = builder.client(getHttpClient()).build()
            return retrofit.create(serviceClass)
        }

        private fun getHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(ErrorCodeInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
        }

        fun retrofit(): Retrofit { return builder.client(getHttpClient()).build() }
    }

}