package filo.mamdouh.weatherforecast.contracts

import filo.mamdouh.weatherforecast.models.LocationItem

interface SearchLocationContract {
    interface View{
        fun onItemClickListener(lon: Double, lat: Double)
        fun onDeleteClicked(locationItem: LocationItem)
    }
    interface Listener{
        fun onSearchLocationClicked()
    }
}