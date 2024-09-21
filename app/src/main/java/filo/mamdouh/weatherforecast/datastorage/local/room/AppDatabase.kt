package filo.mamdouh.weatherforecast.datastorage.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

abstract class AppDatabase : RoomDatabase() {
        abstract val weatherDao: SavedLocationsDao
    companion object{
        private val DB_NAME = "weather_db"
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