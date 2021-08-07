package com.example.gs_weatherapp.services

import com.example.gs_weatherapp.db.CityDao
import com.example.gs_weatherapp.model.GetCurrentWeatherResult
import com.example.gs_weatherapp.model.GetForecastWeatherResult
import com.example.gs_weatherapp.networking.RestAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesWeatherRepository(public val cityDao: CityDao) {

    suspend fun getCityWeather(latitude: Double, longitude: Double): GetForecastWeatherResult =
        withContext(Dispatchers.IO) {
            val response = RestAPI.client.getForecastWeather(latitude, longitude)
            return@withContext response.body()!!
        }

    suspend fun getCityWeather(cityId: Int): GetCurrentWeatherResult =
        withContext(Dispatchers.IO) {
            val response = RestAPI.client.getCurrentWeather(cityId)
            return@withContext response.body()!!
        }

}