package com.example.myapp.apiservice

import com.example.myapp.models.request.TrainRequest
import com.example.myapp.models.response.TrainDetailsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TrainAPIService {
    @POST("/")
    suspend fun getTrainDetails(@Body trainRequest: TrainRequest,
                           @Header("X-RapidAPI-Key")    apiKey : String="4df8556d2bmsh2df755e28304a87p1b7a42jsn0164cab5f4ea",
                           @Header("X-RapidAPI-Host")   apiHost:String ="trains.p.rapidapi.com"):ArrayList<TrainDetailsResponse>

    companion object {
        var apiService: TrainAPIService? = null
        fun getInstance() : TrainAPIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://trains.p.rapidapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(TrainAPIService::class.java)
            }
            return apiService!!
        }
    }
}