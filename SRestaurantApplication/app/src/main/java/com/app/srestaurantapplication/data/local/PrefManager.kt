package com.app.srestaurantapplication.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PrefManager(context: Context) {


    var cnx: Context

    init {
        this.cnx = context
    }

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()


    fun saveString(name: String, value: String) {
        editor.putString(name, value)
        editor.apply()
    }


    fun retrieveString(name: String): String? {
        val saveData = sharedPreferences.getString(name, NO_TOKEN)

        return saveData
    }

    fun saveInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int = 0) = sharedPreferences.getInt(key, defaultValue)

    fun saveIntList(key: String, list: List<Int>) {
        editor.putString(key, Gson().toJson(list))
        editor.apply()
    }

    fun retrieveIntList(key: String): List<Int> {
        val listString = sharedPreferences.getString(key, "[]")

        return Gson().fromJson(listString, IntArray::class.java).toList()
    }

    fun saveBoolean(key: String, boolean: Boolean) {
        editor.putBoolean(key, boolean)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clearPreferences() {
        editor.putString(USER, "")
        editor.putString(TOKEN, "")
        editor.putBoolean(KEY_NOTIFICATION_STATE, true)
        editor.putInt(KEY_UNREAD_NOTIFICATIONS_COUNT, 0)
        editor.apply()



    }
}