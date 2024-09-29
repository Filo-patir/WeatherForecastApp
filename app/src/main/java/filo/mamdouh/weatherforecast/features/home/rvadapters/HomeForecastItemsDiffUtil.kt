package filo.mamdouh.weatherforecast.features.favourite.rvadapters

import androidx.recyclerview.widget.DiffUtil
import filo.mamdouh.weatherforecast.models.ForecastItems

class HomeForecastItemsDiffUtil : DiffUtil.ItemCallback<ForecastItems>(){
    override fun areItemsTheSame(oldItem: ForecastItems, newItem: ForecastItems): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: ForecastItems, newItem: ForecastItems): Boolean {
        return oldItem == newItem
    }

}