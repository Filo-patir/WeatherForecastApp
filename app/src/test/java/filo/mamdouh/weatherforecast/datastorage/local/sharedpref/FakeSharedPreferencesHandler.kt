package filo.mamdouh.weatherforecast.datastorage.local.sharedpref

import kotlinx.coroutines.flow.Flow

class FakeSharedPreferencesHandler : ISharedPreferencesHandler{
    override fun save(key: String?, value: String?) {
        TODO("Not yet implemented")
    }

    override fun get(key: String?): Flow<String> {
        TODO("Not yet implemented")
    }

}