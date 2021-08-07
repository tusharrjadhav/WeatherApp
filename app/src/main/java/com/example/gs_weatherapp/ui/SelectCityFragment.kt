package com.example.gs_weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gs_weatherapp.GSWeatherApp
import com.example.gs_weatherapp.R
import com.example.gs_weatherapp.adapters.SelectCityAdapter
import com.example.gs_weatherapp.databinding.SelectCityFragmentBinding
import com.example.gs_weatherapp.db.City
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModel
import com.example.gs_weatherapp.ui.viewmodels.SharedViewModelFactory

class SelectCityFragment : Fragment() {

    private lateinit var selectCityAdapter: SelectCityAdapter
    private lateinit var selectedFavoriteCities: List<City>
    private var citiesList = mutableListOf<City>()
    private lateinit var binding: SelectCityFragmentBinding
    private val sharedViewModel: SharedViewModel by activityViewModels() {
        SharedViewModelFactory(GSWeatherApp.userRepository)
    }
    var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectCityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // header view
        binding.includeHeader.titleTextView.text = getString(R.string.select_city)
        binding.includeHeader.backBtn.setOnClickListener {
            sharedViewModel.navigateBack(findNavController())
        }
        binding.includeHeader.saveTextView.visibility = View.VISIBLE
        binding.includeHeader.saveTextView.setOnClickListener {
            save()
        }

        binding.rvMultiSelect.layoutManager = LinearLayoutManager(requireContext())
        selectCityAdapter = SelectCityAdapter(mutableListOf())
        // get selected cities
        sharedViewModel.getAllFavoriteCities().observe(viewLifecycleOwner) {
            selectedFavoriteCities = it
            selectCityAdapter = SelectCityAdapter(selectedFavoriteCities.toMutableList())
            binding.rvMultiSelect.adapter = selectCityAdapter
        }

        sharedViewModel.getAllCities().observe(viewLifecycleOwner) {
            citiesList = it
            selectCityAdapter.dataSet = citiesList.toMutableList()
        }

        // search view
        binding.citySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = "%$query%"
                generateList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchQuery = "%$newText%"
                generateList(newText)
                return false
            }
        })
    }

    private fun generateList(search: String) {
        val filteredDataList = if (search.isEmpty()) {
            citiesList
        } else {
            sharedViewModel.findCityByName(search)
        }
        selectCityAdapter.dataSet = filteredDataList.toMutableList()
    }

    private fun save() {
        for (i in 0 until selectCityAdapter.itemCount) {
            if (selectCityAdapter.getItem(i).isFavorite) {
                val updateCity = selectCityAdapter.getItem(i)
                sharedViewModel.updateCityFavoriteCity( updateCity.id, updateCity.isFavorite,)
            }
        }
        sharedViewModel.navigateBack(findNavController())
    }
}