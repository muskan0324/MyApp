package com.example.myapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.apiservice.SpotifyAPIService
import com.example.myapp.models.response.SpotifyTrendingSongsResponse

import kotlinx.coroutines.launch

class SpotifyViewModel:ViewModel() {
    var spotifySongsList:List<SpotifyTrendingSongsResponse> by mutableStateOf(listOf())
    private var spotifyAPIService=SpotifyAPIService.getInstance()
    private var country:String by mutableStateOf("")
    var trackIdsAudioUrls:MutableList<Pair<String,String>> by mutableStateOf( mutableListOf())

    var currentSongUri:String by mutableStateOf("")
    var currentTrackId:String by mutableStateOf("")
    var previousTrackId:String by mutableStateOf("")


    fun getSpotifySongs(){

        viewModelScope.launch {
            try{
                Log.d("c",country)
               spotifySongsList=spotifyAPIService.getTrendingSongs(country)
                Log.d("inside view model-list",spotifySongsList.toString())
        }
            catch (ex:Exception){
            throw Exception(ex.message)
            }
        }
    }
    fun getTrackDetails(trackId:String):String{

        viewModelScope.launch {
            try{
                Log.d("trackid",trackId)
                var trackDetailsResponse= spotifyAPIService.getTrackDetails(trackId)

                currentSongUri= trackDetailsResponse.tracks[0].previewUrl!!
            }
            catch (ex:Exception){
                throw Exception(ex.message)
            }
        }
        return currentSongUri

    }
    fun getCountryCode(): String {
        return country
    }
    fun setCountryCode(countrySelected:String){
       country=countrySelected
    }

}