package filo.mamdouh.weatherforecast.datastorage.local.objectbox

import android.content.Context
import filo.mamdouh.weatherforecast.models.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context)
            .build()
    }
}