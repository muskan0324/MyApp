package com.example.myapp.apiservice

import com.example.myapp.models.response.SpotifyTrackDetailsResponse
import com.example.myapp.models.response.SpotifyTrendingSongsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyAPIService {

    @GET("top_200_tracks")
    suspend fun getTrendingSongs(@Query("country") country:String,@Query("date" )date:String ="2023-07-13",
                         @Header("X-RapidAPI-Key")    apiKey : String="4df8556d2bmsh2df755e28304a87p1b7a42jsn0164cab5f4ea",
                         @Header("X-RapidAPI-Host")   apiHost:String ="spotify81.p.rapidapi.com")
                        :List<SpotifyTrendingSongsResponse>

    @GET("tracks")
    suspend fun getTrackDetails(@Query("ids") trackId:String,
                                @Header("X-RapidAPI-Key")    apiKey : String="4df8556d2bmsh2df755e28304a87p1b7a42jsn0164cab5f4ea",
                                @Header("X-RapidAPI-Host")   apiHost:String ="spotify81.p.rapidapi.com")
                        :SpotifyTrackDetailsResponse


    companion object{
        private const val baseUrl="https://spotify81.p.rapidapi.com/"
        var apiService:SpotifyAPIService?=null

        fun getInstance():SpotifyAPIService{
            if(apiService==null){
               apiService= Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(SpotifyAPIService::class.java)
            }
            return apiService!!
        }

    }
}