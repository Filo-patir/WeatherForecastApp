package filo.mamdouh.weatherforecast.contracts

import filo.mamdouh.weatherforecast.models.CurrentWeather

interface SearchLocationContract {
    interface View{
        fun onItemClickListener(currentWeather: CurrentWeather)
        fun onDeleteClicked(position: Int)
    }
    interface Listener{
        fun onSearchLocationClicked()
    }
}