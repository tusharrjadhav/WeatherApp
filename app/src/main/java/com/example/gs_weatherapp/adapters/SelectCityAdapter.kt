package com.example.gs_weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_weatherapp.databinding.MultiselectRowItemBinding
import com.example.gs_weatherapp.db.City

class SelectCityAdapter(
    var selectedItems: MutableList<City>
) : RecyclerView.Adapter<SelectCityAdapter.CityMultiSelectHolder>() {

    var dataSet: MutableList<City> = mutableListOf()
        set(value) {
            val callback = CitiesDiffCallback(field, value)
            val result = DiffUtil.calculateDiff(callback)
            field = value
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityMultiSelectHolder {
        return CityMultiSelectHolder(MultiselectRowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    fun getItem(position: Int): City = dataSet[position]

    override fun getItemCount(): Int = dataSet.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: CityMultiSelectHolder, position: Int) {
        val field = dataSet[position]
        holder.bind(field)
    }

    inner class CityMultiSelectHolder(private val binding: MultiselectRowItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(field: City) {
            binding.multiSelectItem.apply {
                text = field.toString()
                isChecked = false
                if (selectedItems.any { it.name == field.name }) {
                    isChecked = true
                    field.isFavorite = true
                }
                setOnClickListener {
                    if (selectedItems.size >= 7 && isChecked) {
                        isChecked = false
                        Toast.makeText(context,
                            "You've reached the maximum allowed cities",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        field.isFavorite = isChecked

                        if (selectedItems.any { it.name == field.name }) {
                            if (!isChecked) {
                                selectedItems.remove(field)
                            }
                        } else {
                            if (!isChecked) {
                                selectedItems.remove(field)
                            } else {
                                selectedItems.add(field)
                            }
                        }
                    }
                }
            }

        }
    }
}
