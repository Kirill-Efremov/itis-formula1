package com.kiril.raceapp.di

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class JsonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl: HttpUrl = originalUrl.newBuilder()
            .encodedPath(originalUrl.encodedPath + ".json")
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
