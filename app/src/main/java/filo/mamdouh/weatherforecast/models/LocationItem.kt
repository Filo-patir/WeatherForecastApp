package filo.mamdouh.weatherforecast.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_locations")
data class LocationItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
)