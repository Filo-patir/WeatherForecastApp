package filo.mamdouh.weatherforecast.contracts

interface SettingsUpdater {
    fun checkAndChangLocality(languageCode: String)
}