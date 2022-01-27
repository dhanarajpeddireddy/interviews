package com.pivincii.livedata_retrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

  fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}