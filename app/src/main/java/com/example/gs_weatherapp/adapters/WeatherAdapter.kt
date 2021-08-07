package com.example.gs_weatherapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_weatherapp.R
import com.example.gs_weatherapp.databinding.WeatherRowItemBinding
import com.example.gs_weatherapp.model.WeatherList
import com.example.gs_weatherapp.utils.Utils
import com.example.gs_weatherapp.utils.toCelsius

class WeatherAdapter(internal var list: List<WeatherList>, private var cityName: String) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WeatherRowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = list[position]
        holder.binding.apply {
            timeTxtView.text = Utils.formatDateString(weatherItem.dtTxt!!, "yyyy-MM-dd HH:mm:ss", "EEE dd, MMM 'at' hh:mm a")
            cityTxtView.text = cityName
            val toCelsius = weatherItem.main?.toCelsius()
            tempTxtView.text = toCelsius?.temp.toString() + "°"
            dexrTxtView.text = weatherItem.weather?.get(0)?.description
            minTempTxtView.text = toCelsius?.tempMin.toString() + "° /  " + weatherItem.main?.tempMax.toString() + "°"
            windTxtView.text = weatherItem.wind?.speed.toString() + "km/h"
        }
    }

    override fun getItemCount() = list.size

    class ViewHolder(val binding: WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root)
}
