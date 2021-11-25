package com.app.taybatApplication.data.local

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


    fun saveString(name: String, value: String?) {
        editor.putString(name, value)
        editor.apply()
    }


    fun retrieveString(name: String): String? {
        val saveData = sharedPreferences.getString(name, "")

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

//    fun saveBagList(key: String, list: List<Bag>) {
//        editor.putString(key, Gson().toJson(list))
//        editor.apply()
//    }
//
//    fun retrieveBagList(key: String): List<Any> {
//        val listString = sharedPreferences.getString(key, "[]")
//
//        return Gson().fromJson(listString,AnyArray::class.java)
//    }


    ////


    fun clear(key: String?) {
        editor.remove(key)
        editor.commit()
    }


    fun clearPreferences() {
        editor.putString("city_id","0")
        editor.apply()



    }
}