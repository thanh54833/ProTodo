package com.example.protodo.example.exercise.MVI.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitBuilder {
    private const val BASE_URL = "http://10.0.2.250:3000/"

    //https://stackoverflow.com/questions/32514410/logging-with-retrofit-2
    //var interceptor = HttpLoggingInterceptor()
    //interceptor.setLevel(RestAdapter.LogLevel.FULL);
    //var client = OkHttpClient.Builder().addInterceptor(interceptor)

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        //.client(client.build())
        .build()

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}

