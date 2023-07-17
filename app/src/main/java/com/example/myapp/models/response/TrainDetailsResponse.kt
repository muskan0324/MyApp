package com.example.myapp.models.response

import com.google.gson.annotations.SerializedName

data class TrainDetailsResponse (
    @SerializedName("train_num"  ) var trainNum  : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("train_from" ) var trainFrom : String? = null,
    @SerializedName("train_to"   ) var trainTo   : String? = null,
    @SerializedName("data"       ) var data      : Data?   = Data()
        ) {
    data class Data(
        @SerializedName("id") var id: String? = null,
        @SerializedName("days") var days: Days? = Days(),
        @SerializedName("to_id") var toId: String? = null,
        @SerializedName("classes") var classes: Any?=null,
        @SerializedName("from_id") var fromId: String? = null,
        @SerializedName("arriveTime") var arriveTime: String? = null,
        @SerializedName("departTime") var departTime: String? = null
    )

    data class Days(
        @SerializedName("Fri" ) var Fri : String? = null,
        @SerializedName("Mon" ) var Mon : String? = null,
        @SerializedName("Sat" ) var Sat : String? = null,
        @SerializedName("Sun" ) var Sun : String? = null,
        @SerializedName("Thu" ) var Thu : Int?    = null,
        @SerializedName("Tue" ) var Tue : String? = null,

    )
}
