package com.example.gs_weatherapp.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gs_weatherapp.GSWeatherApp
import com.example.gs_weatherapp.databinding.ActivitySplashBinding
import com.example.gs_weatherapp.db.CityDao
import com.example.gs_weatherapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    private lateinit var cityDao: CityDao
    private lateinit var activity: Activity
    private lateinit var binding: ActivitySplashBinding
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cityDao = GSWeatherApp.appDatabase.cityDao()
        activity = this
        checkCities()
    }

    private fun checkCities() {
        uiScope.launch {
            if (cityDao.getAll().isEmpty()) {
                loadCitiesTask(cityDao)
            } else {
                navigateToCitiesActivity()
            }
        }
    }

    private suspend fun loadCitiesTask(cityDao: CityDao) {
        withContext(Dispatchers.Default){
            try {
                // convert json into a list of Users
                Utils.readJSONFromAsset(activity)?.let {
                    cityDao.insertAll(Utils.fromJson(it))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                navigateToCitiesActivity()
            }
        }
    }

    private fun navigateToCitiesActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
