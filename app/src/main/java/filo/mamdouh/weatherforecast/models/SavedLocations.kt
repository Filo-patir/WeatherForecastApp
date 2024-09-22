package filo.mamdouh.weatherforecast.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_locations")
data class SavedLocations (
    @PrimaryKey
    var id: Int,
    var name: String,
    var latitude: Double,
    var longitude: Double
)