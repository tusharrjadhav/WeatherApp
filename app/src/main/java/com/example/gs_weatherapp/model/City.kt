package com.example.gs_weatherapp.model

import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
}
