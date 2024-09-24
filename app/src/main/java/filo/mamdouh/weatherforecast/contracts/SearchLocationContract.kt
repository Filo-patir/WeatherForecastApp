package filo.mamdouh.weatherforecast.contracts

import filo.mamdouh.weatherforecast.models.LocationItem

interface SearchLocationContract {
    interface View{
        fun onItemClickListener(locationItem: LocationItem)
        fun onDeleteClicked(locationItem: LocationItem)
    }
    interface Listener{
        fun onSearchLocationClicked()
    }
}