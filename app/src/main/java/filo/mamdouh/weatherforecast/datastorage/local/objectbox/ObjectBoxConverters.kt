package filo.mamdouh.weatherforecast.datastorage.local.objectbox

import com.google.gson.Gson
import filo.mamdouh.weatherforecast.models.CachedCompositeKey
import filo.mamdouh.weatherforecast.models.Clouds
import filo.mamdouh.weatherforecast.models.Coord
import filo.mamdouh.weatherforecast.models.ForecastItems
import filo.mamdouh.weatherforecast.models.Main
import filo.mamdouh.weatherforecast.models.Sys
import filo.mamdouh.weatherforecast.models.Weather
import filo.mamdouh.weatherforecast.models.Wind
import io.objectbox.converter.PropertyConverter

class CloudsConverter : PropertyConverter<Clouds, String> {
    override fun convertToEntityProperty(databaseValue: String?): Clouds {
        return Gson().fromJson(databaseValue, Clouds::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: Clouds?): String {
        return Gson().toJson(entityProperty)
    }
}

class CoordConverter : PropertyConverter<Coord, String> {
    override fun convertToEntityProperty(databaseValue: String?): Coord {
        return Gson().fromJson(databaseValue, Coord::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: Coord?): String {
        return Gson().toJson(entityProperty)
    }
}
class MainConverter : PropertyConverter<Main, String> {
    override fun convertToEntityProperty(databaseValue: String?): Main {
        return Gson().fromJson(databaseValue, Main::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: Main?): String {
        return Gson().toJson(entityProperty)
    }
}
class SysConverter : PropertyConverter<Sys, String> {
    override fun convertToEntityProperty(databaseValue: String?): Sys {
        return Gson().fromJson(databaseValue, Sys::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: Sys?): String {
        return Gson().toJson(entityProperty)
    }
}
class WeatherConverter : PropertyConverter<List<Weather>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Weather> {
        return Gson().fromJson(databaseValue, Array<Weather>::class.java).toList()
    }

    override fun convertToDatabaseValue(entityProperty: List<Weather>?): String {
        return Gson().toJson(entityProperty)
    }
}
class WindConverter : PropertyConverter<Wind, String> {
    override fun convertToEntityProperty(databaseValue: String?): Wind {
        return Gson().fromJson(databaseValue, Wind::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: Wind?): String {
        return Gson().toJson(entityProperty)
    }
}
class ForeCastItemConverter : PropertyConverter<List<ForecastItems>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<ForecastItems> {
        return Gson().fromJson(databaseValue, Array<ForecastItems>::class.java).toList()
    }

    override fun convertToDatabaseValue(entityProperty: List<ForecastItems>?): String {
        return Gson().toJson(entityProperty)
    }
}

class CachedCompositeKeyConverter : PropertyConverter<CachedCompositeKey, String>{
    override fun convertToEntityProperty(databaseValue: String?): CachedCompositeKey {
        return Gson().fromJson(databaseValue, CachedCompositeKey::class.java)
    }

    override fun convertToDatabaseValue(entityProperty: CachedCompositeKey?): String {
        return Gson().toJson(entityProperty)
    }
}