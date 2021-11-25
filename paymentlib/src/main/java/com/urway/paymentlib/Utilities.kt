package com.urway.paymentlib

import android.app.Activity
import org.json.JSONObject
import java.nio.charset.Charset

object Utilities {
    fun getAssetData(context: Activity) {
        try {
            val am = context.assets
            val `is` = am.open("appconfig.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            Charset.forName("UTF-8")
            val charset: Charset = Charsets.UTF_8
            val jsonString = String(buffer, charset)
            var jsonObj: JSONObject? = null
            jsonObj = JSONObject(jsonString)
            ConstantsVar.merchanKey = jsonObj["merchantKey"].toString()
            ConstantsVar.termId = jsonObj["terminalId"].toString()
            ConstantsVar.termPass = jsonObj["terminalPass"].toString()
            ConstantsVar.appUrl = jsonObj["requestUrl"].toString()
            //            ConstantsVar.appinitiateTrxn = jsonObj.get("requestUrl").toString();
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}