package com.example.gs_weatherapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherList {

    @SerializedName("main")
    var main: Main? = null

    @SerializedName("weather")
    var weather: List<Weather>? = null

    @SerializedName("wind")
    var wind: Wind? = null

    @SerializedName("dt_txt")
    var dtTxt: String? = null
}

class Wind : Serializable {
    @SerializedName("speed")
    val speed: Double? = null
}

class Weather {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("main")
    var main: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("icon")
    var icon: String? = null
}
