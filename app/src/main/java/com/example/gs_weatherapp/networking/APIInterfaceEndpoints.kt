package com.example.gs_weatherapp.networking

import com.example.gs_weatherapp.model.GetCurrentWeatherResult
import com.example.gs_weatherapp.model.GetForecastWeatherResult
import com.example.gs_weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterfaceEndpoints {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("id") cityID: Int,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<GetCurrentWeatherResult>

    @GET("forecast?")
    suspend fun getForecastWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<GetForecastWeatherResult>
}
