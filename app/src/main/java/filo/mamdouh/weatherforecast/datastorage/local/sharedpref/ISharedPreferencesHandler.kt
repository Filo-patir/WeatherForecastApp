package filo.mamdouh.weatherforecast.datastorage.local.sharedpref

import kotlinx.coroutines.flow.Flow

interface ISharedPreferencesHandler {
    fun save(key: String?, value: String?)
    fun get(key: String?): String
}