package filo.mamdouh.weatherforecast.datastorage.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import filo.mamdouh.weatherforecast.models.SavedLocations
import kotlin.concurrent.Volatile

@Database(entities = [SavedLocations::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
        abstract val weatherDao: SavedLocationsDao
    companion object{
        private const val DB_NAME = "weather_db"
        @Volatile
        private var appDatabase: AppDatabase? = null
        fun getInstance(context: Context):AppDatabase{
            return appDatabase ?: synchronized(this) {
                buildDatabase(context)
            }
        }
        private fun buildDatabase(context: Context):AppDatabase {
            val temp = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
            appDatabase = temp
            return temp
        }
    }
}