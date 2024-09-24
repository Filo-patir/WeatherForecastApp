package filo.mamdouh.weatherforecast.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.Repository
import filo.mamdouh.weatherforecast.datastorage.local.room.SavedLocationDataSource
import filo.mamdouh.weatherforecast.datastorage.local.room.SavedLocationDataSourceImpl
import filo.mamdouh.weatherforecast.datastorage.local.sharedpref.ISharedPreferencesHandler
import filo.mamdouh.weatherforecast.datastorage.local.sharedpref.SharedPreferencesHandler
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSource
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRepository(savedLocationDataSource: SavedLocationDataSource, networkDataSource: NetworkDataSource, sharedPreferencesHandler: ISharedPreferencesHandler): IRepository {
        return Repository(
            savedLocationDataSource,
            networkDataSource,
            sharedPreferencesHandler
        )
    }
    @Provides
    @Singleton
    fun provideSavedLocationDataSource(@ApplicationContext context: Context): SavedLocationDataSource {
        return SavedLocationDataSourceImpl(
            context
        )
    }

    @Provides
    fun provideNetworkDataSource(): NetworkDataSource {
        return NetworkDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesHandler(@ApplicationContext context: Context): ISharedPreferencesHandler {
        return SharedPreferencesHandler(context)
    }
}