package com.example.gs_weatherapp.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gs_weatherapp.GSWeatherApp
import com.example.gs_weatherapp.R
import com.example.gs_weatherapp.adapters.CityAdapter
import com.example.gs_weatherapp.databinding.MainFragmentBinding
import com.example.gs_weatherapp.db.CityDao
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModel
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityDao: CityDao
    private lateinit var binding: MainFragmentBinding
    private val sharedViewModel: SharedViewModel by activityViewModels() {
        SharedViewModelFactory(GSWeatherApp.userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityDao = GSWeatherApp.appDatabase.cityDao()
        binding.favoriteCitiesRecycleView.layoutManager = LinearLayoutManager(context)
        // set click listeners
        binding.infoBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_infoDialog)
        }
        binding.addCityBtn.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSelectCityFragment())
        }

        cityAdapter = CityAdapter {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCitiesWeatherFragment(it.id, it.name, it.coord?.lat?.toLong()?:0, it.coord?.lon?.toLong()?:0))
        }
        binding.favoriteCitiesRecycleView.adapter = cityAdapter
        getFavoriteCities()
    }

    private fun getFavoriteCities() {
        sharedViewModel.getAllFavoriteCities().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                findNavController().navigate(R.id.action_mainFragment_to_infoDialog)
            } else {
                binding.favoriteCitiesRecycleView.visibility = View.VISIBLE
                cityAdapter.dataSet = it
            }
        }
    }

}