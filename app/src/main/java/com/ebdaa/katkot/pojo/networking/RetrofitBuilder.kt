package com.ebada.embassy.pojo.networking

import com.ebada.embassy.pojo.networking.HttpClient.okHttpClient
import com.ebdaasoft.doctorapp_patients.pojo.networking.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    const val BASE_URL = "http://23.111.143.250:6669/TraderService/" //for Debug
    //const val BASE_URL = "http://23.92.71.18:6669/TraderService/"  //Production
    val retrofitBuilder: Retrofit.Builder by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy {

        retrofitBuilder.build()
            .create(ApiService::class.java)
    }

}