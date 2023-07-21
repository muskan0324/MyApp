package com.example.myapp.apiservice

import com.example.myapp.models.response.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPIService {

    @GET("current.json")
    suspend fun getWeather( @Query("q") location:String,
                            @Header("X-RapidAPI-Key")    apiKey : String="APIKEY",
                            @Header("X-RapidAPI-Host")   apiHost:String ="weatherapi-com.p.rapidapi.com"):WeatherResponse

    companion object {
        var apiService: WeatherAPIService? = null
        fun getInstance() : WeatherAPIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://weatherapi-com.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(WeatherAPIService::class.java)
            }
            return apiService!!
        }
    }

}