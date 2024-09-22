package filo.mamdouh.weatherforecast.datastorage.network

sealed class NetworkResponse {
    data class Success(val data: Any?) : NetworkResponse()
    data class Failure(val errorMessage: String) : NetworkResponse()
    object Loading : NetworkResponse()
}