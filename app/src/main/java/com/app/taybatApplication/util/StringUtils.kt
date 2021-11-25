package com.app.taybatApplication.util


fun String.isValidEmail() : Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}