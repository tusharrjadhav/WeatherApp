package com.example.gs_weatherapp.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_weatherapp.databinding.CityRowItemBinding
import com.example.gs_weatherapp.db.City

class CityAdapter(private val callback: ((City) -> Unit)?) :
    RecyclerView.Adapter<CityAdapter.CityHolder>() {

    var dataSet: MutableList<City> = mutableListOf()
        set(value) {
            val callback = CitiesDiffCallback(field, value)
            val result = DiffUtil.calculateDiff(callback)
            field = value
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder(CityRowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        val field = dataSet[position]
        holder.bind(field)
    }

    inner class CityHolder(private val binding: CityRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(field: City) {
            binding.cityItemText.text = field.name
            binding.counttryItemText.text = field.country

            binding.holderLayout.setOnClickListener {
                callback?.invoke(field)
            }
        }
    }
}
