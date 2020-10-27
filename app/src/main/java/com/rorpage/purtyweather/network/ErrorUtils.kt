package com.rorpage.purtyweather.network

import com.rorpage.purtyweather.models.ApiError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

object ErrorUtils {

    private val retrofit = ServiceGenerator.retrofit()

    fun parseError(response: Response<*>): ApiError{
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