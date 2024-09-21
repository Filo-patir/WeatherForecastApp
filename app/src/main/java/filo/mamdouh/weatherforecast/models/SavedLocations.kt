package filo.mamdouh.weatherforecast.models

import androidx.room.Entity

@Entity(tableName = "saved_locations")
data class SavedLocations (
    var id: Int,
    var name: String,
    var latitude: Double,
    var longitude: Double
)