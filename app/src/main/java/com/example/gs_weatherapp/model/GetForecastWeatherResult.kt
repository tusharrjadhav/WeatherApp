package com.example.gs_weatherapp.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

class GetForecastWeatherResult {

    @SerializedName("city")
    var city: City? = null

    @SerializedName("list")
    var list: List<WeatherList>? = null
}
