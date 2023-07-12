package com.example.myapp.models.response

import com.google.gson.annotations.SerializedName

data class MemesResponse (
    @SerializedName("id") var id:String?= null,
    @SerializedName("name") var name:String?= null,
    @SerializedName("lines") var lines:Int?= null,
    @SerializedName("overlays") var overlays:Int?= null,
    @SerializedName("styles") var styles: ArrayList<String>?=arrayListOf(),
    @SerializedName("blank") var  blank:String?=null,
    @SerializedName("example") var example:ExampleResponse?= null,
    @SerializedName("source") var source:String?= null,
    @SerializedName("_self") var self:String?= null,


    )
