package com.urway.paymentlib

import android.app.AlertDialog
import android.content.Context

//import com.concerto.paymentlib.R;
internal class AlertDialogManager {
    fun showAlertDialog(context: Context?, title: String?, message: String?,
                        status: Boolean?) {
        val alertDialog = AlertDialog.Builder(context).create()

        // Setting Dialog Title
        alertDialog.setTitle(title)

        // Setting Dialog Message
        alertDialog.setMessage(message)
        if (status != null) // Setting alert dialog icon
            alertDialog.setIcon(if (status) R.drawable.app_logo else R.drawable.app_logo)

        // Setting OK Button
        alertDialog.setButton("OK") { dialog, which -> }

        // Showing Alert Message
        alertDialog.show()
    }
}