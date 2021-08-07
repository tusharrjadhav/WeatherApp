package com.example.gs_weatherapp.model

import com.google.gson.annotations.SerializedName

class Main {
    @SerializedName("temp")
    var temp: Double = 0.0
    @SerializedName("temp_min")
    var tempMin: Double = 0.0
    @SerializedName("temp_max")
    var tempMax: Double = 0.0
}
