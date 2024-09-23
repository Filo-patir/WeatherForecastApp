package filo.mamdouh.weatherforecast.features.search.main.adapter

import androidx.recyclerview.widget.DiffUtil
import filo.mamdouh.weatherforecast.models.CurrentWeather

class SavedLocationDiffUtils : DiffUtil.ItemCallback<CurrentWeather>() {
    override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
        return oldItem == newItem
    }
}