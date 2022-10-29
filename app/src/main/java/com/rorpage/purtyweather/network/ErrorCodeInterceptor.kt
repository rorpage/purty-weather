package com.rorpage.purtyweather.network

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorCodeInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        val newCode = response.code

        return response.newBuilder()
                .body(response.body)
                .code(newCode)
                .build()
    }
}