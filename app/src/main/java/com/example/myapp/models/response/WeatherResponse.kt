package com.example.myapp.models.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current()

)