package com.example.myapp.models.request

import com.google.gson.annotations.SerializedName

data class TrainRequest(

    @SerializedName("search") var trainName  : String?    = null,
)