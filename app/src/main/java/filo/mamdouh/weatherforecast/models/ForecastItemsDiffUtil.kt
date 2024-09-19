package filo.mamdouh.weatherforecast.models

import androidx.recyclerview.widget.DiffUtil

class ForecastItemsDiffUtil : DiffUtil.ItemCallback<ForecastItems>(){
    override fun areItemsTheSame(oldItem: ForecastItems, newItem: ForecastItems): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: ForecastItems, newItem: ForecastItems): Boolean {
        return oldItem == newItem
    }

}