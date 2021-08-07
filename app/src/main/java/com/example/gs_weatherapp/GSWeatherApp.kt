package com.example.gs_weatherapp

import android.app.Application
import com.example.gs_weatherapp.db.WeatherRoomDatabase
import com.example.gs_weatherapp.services.CitiesWeatherRepository


class GSWeatherApp : Application() {
    companion object {
        lateinit var appDatabase: WeatherRoomDatabase
        lateinit var userRepository: CitiesWeatherRepository
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = WeatherRoomDatabase(this)
        userRepository  = CitiesWeatherRepository(appDatabase.cityDao())
    }
}
