package com.app.taybatApplication.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.app.taybatApplication.R
import com.google.android.material.snackbar.Snackbar

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.showSnackbar(text: String, duration: Int = Snackbar.LENGTH_SHORT ,actionText: Int?= null, anchorView: View? = null,
                      action: (() -> Unit)? = null) {

    val snackbar = Snackbar.make(this, text, duration)

    val sbView = snackbar.view
    val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
    textView.setTextColor(Color.BLACK)

    if(actionText != null) snackbar.setAction(actionText) {
        action?.invoke()
    }
    anchorView?.let {
        snackbar.anchorView = it
    }
    snackbar.show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

