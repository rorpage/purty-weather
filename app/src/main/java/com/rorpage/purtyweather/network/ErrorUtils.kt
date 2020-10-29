package com.rorpage.purtyweather.network

import android.content.Context
import com.rorpage.purtyweather.models.ApiError
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class ErrorUtils(val applicationContext: Context) {

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface RetrofitInterface {
       fun retrofit(): Retrofit
    }

    fun parseError(response: Response<*>): ApiError{
        val retrofit: Retrofit = EntryPointAccessors.fromApplication(applicationContext, RetrofitInterface::class.java).retrofit()
        val converter: Converter<ResponseBody, ApiError> = retrofit
                .responseBodyConverter(ApiError::class.java, arrayOfNulls<Annotation>(0))

        val error: ApiError?

        try {
            error = converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            return ApiError(false, "An error occurred.")
        }

        return error ?: ApiError(false, "An error occurred.")
    }
}