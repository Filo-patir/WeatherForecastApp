package filo.mamdouh.weatherforecast.datastorage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASEURL = "https://api.openweathermap.org/data/2.5/"
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val apiServices: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}