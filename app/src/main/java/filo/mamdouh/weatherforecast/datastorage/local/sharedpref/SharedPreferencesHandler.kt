package filo.mamdouh.weatherforecast.datastorage.local.sharedpref

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SharedPreferencesHandler @Inject constructor(context: Context) : ISharedPreferencesHandler {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("weather-forecast", Context.MODE_PRIVATE)

    override fun save(key: String?, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun get(key: String?): Flow<String> {
        return flow{
            emit(sharedPreferences.getString(key, "")?: "")
        }
    }
}