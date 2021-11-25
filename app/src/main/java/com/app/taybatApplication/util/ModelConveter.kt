package com.app.taybatApplication.util

import android.content.Context
import android.widget.Toast
import com.app.taybatApplication.data.local.PRICE
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ModelConveter {

    fun convertArabic(arabicStr: String?): Double {

        if (arabicStr == null) {
            return 0.0
        } else {
            val chArr = arabicStr.toCharArray()
            val sb = StringBuilder()
            for (ch in chArr) {
                if (Character.isDigit(ch)) {
                    sb.append(Character.getNumericValue(ch))
                } else if (ch == '٫') {
                    sb.append(".")
                } else {
                    sb.append(ch)
                }
            }
            return sb.toString().toDouble()
        }
    }

     fun logOut(context: Context) {

         val prefManager= PrefManager(context)
         val token="Bearer".plus(prefManager.retrieveString(TOKEN))

         RetrofitClient.instance.logOut(token).enqueue(object : Callback<ResponseDto<String>> {
             override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {
                 Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()
             }

             override fun onResponse(
                 call: Call<ResponseDto<String>>,
                 response: Response<ResponseDto<String>>
             ) {
                 if (response.code()==200)
                 {
                     if (response.isSuccessful)
                     {
                         prefManager.clear(TOKEN)
                         prefManager.clear(PRICE)
                         context.navigateToRegistration()

                     }
                 }
             }
         })
    }
}