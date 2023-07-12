package com.example.myapp.models.response

import com.google.gson.annotations.SerializedName

data class ExampleResponse(
    @SerializedName("text") var text:ArrayList<String>?= arrayListOf(),
    @SerializedName("url") var url:String?= null,
)