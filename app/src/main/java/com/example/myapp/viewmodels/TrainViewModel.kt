package com.example.myapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.apiservice.TrainAPIService
import com.example.myapp.models.request.TrainRequest
import com.example.myapp.models.response.TrainDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.http.Body
import kotlin.time.Duration.Companion.seconds

class TrainViewModel:ViewModel(){

    var trainDetailsList:ArrayList<TrainDetailsResponse> by mutableStateOf(arrayListOf())
    var trainAPIService=TrainAPIService.getInstance()

    fun getTrainDetailsList(trainRequest: TrainRequest){
        viewModelScope.launch (Dispatchers.IO){
            try {
               trainDetailsList=trainAPIService.getTrainDetails( trainRequest=trainRequest)
                Log.d("list",trainDetailsList.toString())
            }
            catch(ex:Exception){
                throw Exception(ex.message)
            }
        }
    }

}