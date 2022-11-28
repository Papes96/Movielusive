package com.example.data.api

import com.example.data.CurlLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceProvider {
    fun getRetrofit() : IService {


        // TODO add if(debug)
        val interceptor = CurlLoggingInterceptor()
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(interceptor)
        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/") //10.0.2.2
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(IService::class.java)
    }
}