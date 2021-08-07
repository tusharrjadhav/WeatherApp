package com.example.gs_weatherapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var country: String = "",
    var isFavorite: Boolean = false,
    var isCurrent: Boolean = false,
    @Embedded var coord: Coord? = null
) {
    override fun toString(): String {
        return "City: $name\nCountry: $country"
    }
}

data class Coord(
    var lon: Double = 0.0,
    var lat: Double = 0.0
)
