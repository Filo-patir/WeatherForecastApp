package filo.mamdouh.weatherforecast.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "saved_locations",
    indices = [Index(value = ["name"], unique = true)]
)
data class LocationItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    var home:Boolean = false
)