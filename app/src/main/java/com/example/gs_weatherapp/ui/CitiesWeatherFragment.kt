package com.example.gs_weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gs_weatherapp.GSWeatherApp
import com.example.gs_weatherapp.R
import com.example.gs_weatherapp.adapters.WeatherAdapter
import com.example.gs_weatherapp.model.GetCurrentWeatherResult
import com.example.gs_weatherapp.model.GetForecastWeatherResult
import com.example.gs_weatherapp.databinding.CitiesWeatherFragmentBinding
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModel
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModelFactory
import com.example.gs_weatherapp.utils.Utils
import com.example.gs_weatherapp.utils.toCelsius

class CitiesWeatherFragment : Fragment() {
    private lateinit var binding: CitiesWeatherFragmentBinding
    private val params by navArgs<CitiesWeatherFragmentArgs>()
    private val sharedViewModel: SharedViewModel by activityViewModels() {
        SharedViewModelFactory(GSWeatherApp.userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CitiesWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeWeatherRow.root.visibility = View.GONE

        binding.includeHeader.backBtn.setOnClickListener {
            sharedViewModel.navigateBack(findNavController())
        }

        getCurrentWeather(params.selectedCity)
        binding.includeHeader.deleteBtn.visibility = View.VISIBLE
        getForecastWeather(params.latitude.toDouble(), params.longitude.toDouble())

        binding.includeHeader.deleteBtn.setOnClickListener {

            Utils.showDialogActions(requireContext(), getString(R.string.remove_alert), "Ok", getString(R.string.cancel), {
                sharedViewModel.updateCityFavoriteCity(params.selectedCity, false)
                sharedViewModel.navigateBack(findNavController())
            },
                { return@showDialogActions })
        }
    }


    @SuppressLint("SetTextI18n")
    fun updateView(weatherResult: GetCurrentWeatherResult) {
        binding.includeWeatherRow.apply {
            timeTxtView.text = Utils.convertDate(weatherResult.dt!!.toLong(), "hh:mm a")
            cityTxtView.text = weatherResult.name
            val toCelsius = weatherResult.main?.toCelsius()
            tempTxtView.text = toCelsius?.temp.toString() + "°"
            dexrTxtView.text = weatherResult.weather?.get(0)?.description
            minTempTxtView.text =
                toCelsius?.tempMin.toString() + "°  /  " + weatherResult.main?.tempMax.toString() + "°"
            windTxtView.text = weatherResult.wind?.speed.toString() + " km/h"
        }
    }

    private fun getCurrentWeather(selectedCityID: Int) {
        if (Utils.checkNetwork(context)) {
            binding.progressBar.visibility = View.VISIBLE
            sharedViewModel.getCurrentWeather(selectedCityID).observe(viewLifecycleOwner) {
                binding.progressBar.visibility = View.GONE
                binding.includeWeatherRow.root.visibility = View.VISIBLE
                binding.includeHeader.titleTextView.text = it.name
                updateView(it)
            }
        } else {
            Utils.showDialogActions(requireContext(), getString(R.string.no_internet), getString(R.string.retry), getString(
                R.string.cancel), {
                getCurrentWeather(selectedCityID)
            }, null)
        }
    }

    private fun getForecastWeather(latitude: Double, longitude: Double) {
        if (Utils.checkNetwork(activity)) {
            binding.progressBar.visibility = View.VISIBLE
            sharedViewModel.getForecastWeather(latitude, longitude).observe(viewLifecycleOwner) {
                binding.progressBar.visibility = View.GONE
                binding.includeHeader.titleTextView.text = it.city?.name ?: ""
                displayForecastWeather(it)
            }
        } else {
            Utils.showDialogActions(requireContext(), getString(R.string.no_internet), getString(R.string.retry), getString(
                R.string.cancel), {
                getForecastWeather(latitude, longitude)
            }, null)
        }
    }

    private fun displayForecastWeather(getForecastWeatherResult: GetForecastWeatherResult) {

        binding.weatherRecycleView.apply {
            visibility = View.VISIBLE
            hasFixedSize()
            val llManager = LinearLayoutManager(context)
            layoutManager = llManager
            addItemDecoration(DividerItemDecoration(context, llManager.orientation))
            adapter = WeatherAdapter(getForecastWeatherResult.list!!, params.selectedName)
        }
    }
}