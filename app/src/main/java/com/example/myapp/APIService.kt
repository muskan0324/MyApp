package com.example.myapp

import com.example.myapp.models.response.MemesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface APIService {

    @GET("templates")
    suspend fun getMemes() : ArrayList<MemesResponse>

    companion object {
        var apiService: APIService? = null
        fun getInstance() : APIService{
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://api.memegen.link/")

                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }

}