package com.apricity.libnet

import retrofit2.Retrofit

object HttpUtil {
    private const val BASE_URL = "https://api.github.com/"
    private var retrofit = Retrofit.Builder().baseUrl(BASE_URL).build()

    fun <T> createService(serviceClass: Class<T>) : T = retrofit.create(serviceClass)
}