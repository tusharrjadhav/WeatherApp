package com.example.gs_weatherapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gs_weatherapp.GSWeatherApp
import com.example.gs_weatherapp.R
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModel
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModelFactory

class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels() {
        SharedViewModelFactory(GSWeatherApp.userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
