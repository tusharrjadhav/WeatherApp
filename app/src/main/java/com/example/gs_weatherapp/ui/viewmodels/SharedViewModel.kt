package com.example.gs_weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gs_weatherapp.GSWeatherApp
import com.example.gs_weatherapp.model.GetCurrentWeatherResult
import com.example.gs_weatherapp.model.GetForecastWeatherResult
import com.example.gs_weatherapp.db.City
import com.example.gs_weatherapp.services.CitiesWeatherRepository
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: CitiesWeatherRepository) : ViewModel() {

    private val allCities: MutableLiveData<MutableList<City>> = MutableLiveData()
    private val cities: MutableLiveData<MutableList<City>> = MutableLiveData()
    private val favoriteCities: MutableLiveData<MutableList<City>> = MutableLiveData()

    private val currentWeatherResult: MutableLiveData<GetCurrentWeatherResult> = MutableLiveData()
    private val forecastWeatherResult: MutableLiveData<GetForecastWeatherResult> = MutableLiveData()

    fun getAllCities() : LiveData<MutableList<City>> {
        viewModelScope.launch {
            allCities.value = repository.cityDao.getAll().toMutableList()
        }
        return allCities
    }

    fun getAllFavoriteCities() : LiveData<MutableList<City>> {
        viewModelScope.launch {
            favoriteCities.value = repository.cityDao.getAllFavoriteCities().toMutableList()
        }
        return favoriteCities
    }

    fun getCurrentWeather(cityId: Int): LiveData<GetCurrentWeatherResult> {
        viewModelScope.launch {
            currentWeatherResult.value = repository.getCityWeather(cityId)
        }
        return currentWeatherResult
    }

    fun getForecastWeather(lat: Double, lon: Double): LiveData<GetForecastWeatherResult> {
        viewModelScope.launch {
            forecastWeatherResult.value = repository.getCityWeather(lat, lon)
        }
        return forecastWeatherResult
    }

    fun updateCityFavoriteCity(cityId: Int, isFavorite: Boolean) {
        repository.cityDao.updateCity(isFavorite, cityId)
    }

    fun findCityByName(search: String): List<City> {
        return repository.cityDao.findByNameCountry(search, search)
    }

    fun navigateBack(navController: NavController) {
        navController.popBackStack()
    }
}

class SharedViewModelFactory(private val repository: CitiesWeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}