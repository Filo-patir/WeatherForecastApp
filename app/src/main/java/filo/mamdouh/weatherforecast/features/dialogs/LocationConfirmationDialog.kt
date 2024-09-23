package filo.mamdouh.weatherforecast.features.dialogs

import android.content.Context
import filo.mamdouh.weatherforecast.contracts.SearchLocationContract

object LocationConfirmationDialog {
    fun showDialog(context: Context, listener: SearchLocationContract.Listener, location : String) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Confirm Location")
        builder.setMessage("Are you sure you want to pick $location?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            listener.onSearchLocationClicked()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}