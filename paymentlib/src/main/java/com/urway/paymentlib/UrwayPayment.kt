package com.urway.paymentlib

import android.app.Activity
import android.app.FragmentManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.StrictMode
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.urway.paymentlib.Checkout.Companion.generatedTrxnJson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.NetworkInterface
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class UrwayPayment {
    var checkout = Checkout()
    private var alert = AlertDialogManager()
    private var targetUrl: String? = null
    var payId: String? = null
    private var result: String? = null
    private var respCode: String? = null
    private var authCode: String? = null
    private var tranId: String? = null
    private var errorResponse: String? = null
    private var redirectUrl: String? = null
    var jsonObjecttrxn: JSONObject? = null
    var amt: String? = null
    var usr_Fld10: String? = null
    private val fragManager: FragmentManager? = null
    var IPaddress: String? = null
    var spl: String? = null
    var weburl: String? = null

    @Throws(ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class, IOException::class, JSONException::class)
    fun makepaymentService(amountd: String, context: Activity, action_Code: String, Currency: String, merchant_Ip: String? /*, String trans_Id*/, usr_Fld1: String?, usr_Fld2: String?,
                           usr_Fld3: String?, usr_Fld4: String?, usr_Fld5: String?, email: String, address: String?,
                           city: String?, state_code: String?, zip: String?, Country_Code: String, TrackID: String,
                           cardOperation: String, cardToken: String, tokenType: String?) {
        System.out.println("savita amountd" + amountd);
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Utilities.getAssetData(context)
        if(checkout.isOnline(context)){
        if (ResponseConfig.startTrxn != ConstantsVar.appinitiateTrxn) {
            ResponseConfig.startTrxn = true
            amt = amountd;
            if (isValidationSucess(context, amountd, email, action_Code, Country_Code, Currency, TrackID, cardOperation, cardToken, zip)) {
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
                val format = simpleDateFormat.format(Date())
                //         generate json object
                IPaddress = getIPAddress(context)
                val generatedJson = checkout.generateJson(ConstantsVar.termId,
                        ConstantsVar.termPass, action_Code, Currency, amt, IPaddress, merchant_Ip /*, trans_Id*/
                        , usr_Fld1, usr_Fld2, usr_Fld3, usr_Fld4, usr_Fld5, email, address, city, state_code, zip,
                        Country_Code, ConstantsVar.merchanKey, TrackID, cardOperation, cardToken, tokenType)
                println("response Request generatedJson =$generatedJson")

                //generate hashValue
                val hashValue = checkout.generateHashKey(generatedJson, ConstantsVar.merchanKey)
                println("amountd = " + amountd + ", context = " + context + ", initFlag = " + ConstantsVar.appinitiateTrxn + ", action_Code = " + action_Code + ", hashValue = " + hashValue)
                val response = ""
                try {
                    //call pg transaction
                    postData(ConstantsVar.appUrl, generatedJson, hashValue,
                            "5000", context)
                    //
                    extractData(response, context)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ResponseConfig.startTrxn = false
                    setErrorResponse("Unable to get response from configured url")
                    println("timeout Unable to get response from configured url ")
                    alert.showAlertDialog(context, "Timeout..", "Service is not responding", false)
                }
            }
        } else {
            Toast.makeText(context, "Transaction already Initiated", Toast.LENGTH_SHORT).show()
           // ResponseConfig.startTrxn = false
        }
    }
        else{
            ResponseConfig.startTrxn = false
            Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show();

             }
        //        return
    }





    fun extractData(xmlData: String?, context: Activity?) {
        var json: JSONObject? = null
        val merchantKey = "secret"
        try {
            json = JSONObject(xmlData)
        } catch (e1: Exception) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        var targetUrl: String? = ""
        var payid: String? = ""
        var authcode: String? = ""
        var result: String? = ""
        var tranid: String? = ""
        var responsecode: String? = ""
        val resphash = ""
        if (json != null) {
            try {
                if (json.getString("targetUrl") != null) {
                    targetUrl = json.getString("targetUrl")
                }
                if (json.getString("payid") != null) {
                    payid = json.getString("payid")
                    //                  ApplicationConfiguaration.payId = payId;
                }
                authcode = json.getString("authcode")
                result = json.getString("result")
                tranid = json.getString("tranid")
                //                ApplicationConfiguaration.tranId = tranId;
                responsecode = json.getString("responseCode")
                if (targetUrl != null && !targetUrl.equals("null", ignoreCase = true)) {
                    setTargetUrl(targetUrl)
                    payId = payid
                    val payIdReplace = payId
                    payId = payIdReplace!!.replace("&amp;", "&")
                    /** */
                    println("This is final respnse call")
                    println(targetUrl)
                    println(payId)
                    //                    setRedirectUrl(targetUrl + "?" + "paymentid=" + payId);
//                    String weburl = targetUrl + "?" + "paymentid=" + payId;
//                    System.out.println("weburl= " + weburl);
                    val lastCharUrl = targetUrl[targetUrl.length - 1]
                    if (lastCharUrl == '?') {
                        setRedirectUrl(targetUrl + "paymentid=" + payId)
                        weburl = targetUrl + "paymentid=" + payId
                        println("weburl if= $weburl")
                    } else {
                        setRedirectUrl("$targetUrl?paymentid=$payId")
                        weburl = "$targetUrl?paymentid=$payId"
                        println("weburl else= $weburl")
                    }
                    try {
                        //                       map response to call webview
//                        Intent intent = new Intent(context, Class.forName("com.example.pgsdk.Checkout"));
//                        intent.putExtra("weburl", weburl);
//                        context.startActivity(intent);
//                        Intent intent = new Intent(context, Class.forName("com.example.pgsdk.Checkout"));
//                        context.startActivityForResult(intent, 2);// Activity is started with requestCode 2
                        jsonObjecttrxn = getwebviewData(context, weburl,"")
                        //                        Intent intent = new Intent(context, Checkout.class);
//                        intent.putExtra("weburl", weburl);
////                      intent.putExtra("loadurl", initialFlag);
//                        context.startActivityForResult(intent, 2);
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                else if(responsecode.equals("512") ||responsecode.equals("000")){
                    var amount: String? = ""
                    var cardToken: String? = ""
                    amount = json.getString("amount")
                    cardToken = json.getString("cardToken")

                    val format = DecimalFormat("##.##")
                    var formatted = format.format(amount!!.toDouble())
                    if (formatted.contains(".")) {
                        val valnumber = formatted.substring(formatted.indexOf(".")).substring(1)
                        if (valnumber.length == 1) {
                            formatted = formatted + "0"
                        }
                    } else {
                        formatted = "$formatted.00"
                    }
                    ResponseConfig.startTrxn = false
                    ResponseConfig.loadurl = false
                    ResponseConfig.finalRespJson = generatedTrxnJson
                    generatedTrxnJson = checkout.generateTrxnRespJson(tranid, responsecode, formatted, result, payid, cardToken)
                    println(generatedTrxnJson.toString())
                    jsonObjecttrxn = getwebviewData(context, weburl,generatedTrxnJson.toString())                }

                else {

                    //todo traget url is null
                    setAuthCode(authcode)
                    setResult(result)
                    setTranId(tranid)
                    setResponseCode(responsecode)
                    val resp = ResponseConfig()
                    var resMsg = resp.respCode[responsecode]
                    if (resMsg == null || resMsg.isEmpty()) {
                        resMsg = "Invalid Response"
                    }
                    generateFailureResponseJson(result, responsecode, resMsg)
                    ResponseConfig.startTrxn = false
                    alert.showAlertDialog(context, "Error ", resMsg, false)
                    Toast.makeText(context, resMsg, Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
    }

    fun setRedirectUrl(redirectUrl: String?) {
        this.redirectUrl = redirectUrl
    }

    fun setErrorResponse(errorResponse: String?) {
        this.errorResponse = errorResponse
    }

    fun setTargetUrl(target_Url: String?) {
        targetUrl = target_Url
    }

    fun setAuthCode(auth_Code: String?) {
        authCode = auth_Code
    }

    fun setTranId(tran_Id: String?) {
        tranId = tran_Id
    }

    fun setResponseCode(resp_Code: String?) {
        respCode = resp_Code
    }

    fun setResult(Result: String?) {
        result = Result
    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
                val status = data!!.getStringExtra("data")
                //Do what u want with data
            }
        } catch (e: Exception) {
            println("=====Exception=====$e")
        }
    }

    fun generateFailureResponseJson(result: String?, responseCode: String?, responseMsg: String?): JSONObject {
        val testJson = JSONObject()
        try {
            testJson.put("Result", result)
            testJson.put("ResponseCode", responseCode)
            testJson.put("ResponseMsg", responseMsg)
        } catch (e1: JSONException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        //String json = testJson.toString();
        return testJson
    }

    fun getwebviewData(context: Activity?, weburl: String?, jsonString: String): JSONObject? {
        var jsonobject: JSONObject? = null
        System.out.println("res getwebviewData jsonString:"+jsonString)
        if(jsonString.equals("")){
            val intent = Intent(context, Checkout::class.java)
            intent.putExtra("weburl", weburl)
            intent.putExtra("amt", amt)
            intent.putExtra("payid", payId)
            intent.putExtra("payid", payId)
            intent.putExtra("jsonstring", jsonString)
            ResponseConfig.loadurl = true
            //                        intent.putExtra("loadurl", initialFlag);
            context!!.startActivityForResult(intent, 2)
        }
        else{
            val intent = Intent(context, Checkout::class.java)
            intent.putExtra("weburl", weburl)
            intent.putExtra("amt", amt)
            intent.putExtra("payid", payId)
            intent.putExtra("jsonstring", jsonString)
            ResponseConfig.loadurl = true
            //                        intent.putExtra("loadurl", initialFlag);
            context!!.startActivityForResult(intent, 2)
        }
        if (ResponseConfig.finalRespJson != null) {
            jsonobject = ResponseConfig.finalRespJson
        }
        return jsonobject
    }

    /* public void DisplayJson(JSONObject respData)
    {
        try {
            String Amount=respData.get("amount").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respData;
    }*/
    fun getIPAddress(context: Context): String? {
        var WIFI = false
        var MOBILE = false
        val CM = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = CM.allNetworkInfo
        for (netInfo in networkInfo) {
            if (netInfo.typeName.equals("WIFI", ignoreCase = true)) if (netInfo.isConnected) WIFI = true
            if (netInfo.typeName.equals("MOBILE", ignoreCase = true)) if (netInfo.isConnected) MOBILE = true
        }
        if (WIFI == true) {
            IPaddress = GetDeviceipWiFiData(context)
        }
        if (MOBILE == true) {
            IPaddress = GetDeviceipMobileData()
        }
        return IPaddress
    }

    fun isValidationSucess(context: Context?, amount: String, email: String, Action: String, CountryCode: String,
                           Currency: String, track: String, cardOperation: String, cardToken: String, zip: String?): Boolean {
        var d = false
        //        double am=Double.parseDouble(amount);
        val validcheck = isValidEmail(email)
        if (amount.isEmpty()) {
            alert.showAlertDialog(context, "Error", "Amount Should not be Empty", false)
            ResponseConfig.startTrxn = false
//        } else if (zip?.length==0 || zip!!.isEmpty() ) {
//            alert.showAlertDialog(context, "Error", "Zip Code Should not be Empty", false)
//            ResponseConfig.startTrxn = false
//        }
        }
        else if (email.isEmpty()) {
            alert.showAlertDialog(context, "Error", "Email Should not be Empty", false)
            ResponseConfig.startTrxn = false
        }
        else if (Action.isEmpty() || Action.length == 0) {
            alert.showAlertDialog(context, "Error", "Action Code should not be Empty", false)
            ResponseConfig.startTrxn = false
        } else if (Currency.isEmpty() || Currency.length == 0) {
            alert.showAlertDialog(context, "Error", "Currency should not be Empty", false)
            ResponseConfig.startTrxn = false
        } else if (CountryCode.isEmpty() || CountryCode.length == 0) {
            alert.showAlertDialog(context, "Error", "Country Code should not be Empty", false)
            ResponseConfig.startTrxn = false
        } else if (track.isEmpty() || track.length == 0) {
            alert.showAlertDialog(context, "Error", "Track ID should not be Empty", false)
            ResponseConfig.startTrxn = false
        } else if (Currency.length > 3) {
            alert.showAlertDialog(context, "Error", "Currency should be proper ", false)
            ResponseConfig.startTrxn = false
        } else if (Action.length > 3) {
            alert.showAlertDialog(context, "Error", "Action Code should be proper ", false)
            ResponseConfig.startTrxn = false
        } else if (CountryCode.length > 2) {
            alert.showAlertDialog(context, "Error", "CountryCode should be proper ", false)
            ResponseConfig.startTrxn = false
        } else if (!email.isEmpty() && validcheck == false) {
            alert.showAlertDialog(context, "Error", "Email should be proper", false)
            ResponseConfig.startTrxn = false
        } else if (Action.equals("12", ignoreCase = true) && cardOperation.equals("U", ignoreCase = true) && cardToken.isEmpty()) {
            alert.showAlertDialog(context, "Error", "Card Token should not be Empty", false)
            ResponseConfig.startTrxn = false
        } else if (Action.equals("12", ignoreCase = true) && cardOperation.equals("D", ignoreCase = true) && cardToken.isEmpty()) {
            alert.showAlertDialog(context, "Error", "Card Token should not be Empty", false)
            ResponseConfig.startTrxn = false
        } else if (Action.equals("12", ignoreCase = true) && !(cardOperation.equals("A", ignoreCase = true) || cardOperation.equals("U", ignoreCase = true) || cardOperation.equals("D", ignoreCase = true))) {
            alert.showAlertDialog(context, "Invalid Tokenization", "Card Operation is not proper ", false)
            ResponseConfig.startTrxn = false
        }

        else if (Action.equals("14")) {
            if (cardToken.length==0) {
                alert.showAlertDialog(context, "Error", "Card Token Should not be Empty", false)
                ResponseConfig.startTrxn = false
            }
            else{
                d = true
            }
        }
        else {
            d = true
        }
        return d
    }

    fun postData(requesturl: String?, jsondata: JSONObject, hashValue: String?, pgServiceReadtime: String?, context: Activity?) {
        val pDialog = ProgressDialog(context)
        try {
            HttpsTrustManager.allowAllSSL()
            jsondata.put("requestHash", hashValue)
            pDialog.setMessage("Loading...")
            //            if(!(context).isFinishing())
            if (context != null && !context.isFinishing) {
                pDialog.show()
            }
            //        JSONObject object = new JSONObject();
//        try {
//            //input your API parameters
//            object.put("parameter","value");
//            object.put("parameter","value");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Enter the correct url for your api service site
//        String url = getResources().getString(R.string.url);
            val mRequestBody = jsondata.toString()
            //            StringRequest stringRequest
            val jsonObjectRequest = JsonObjectRequest(requesturl, jsondata,
                    Response.Listener { response ->
                        pDialog.dismiss()
                        Log.d("TAG RESP sucess", response.toString())
                        spl = response.toString()
                        println("String Volley Res: $response")
                        extractData(response.toString(), context)
                    },
                    Response.ErrorListener { error ->
                        pDialog.dismiss()
                        //                         resultTextView.setText("Error getting response");
                        spl = error.message
                        Log.d("TAG RESP fail", error.toString())
                    })
            val requestQueue = Volley.newRequestQueue(context)
            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            requestQueue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //        return spl;
    }

    companion object {
        var initialFlag = false
        var PACKAGE_NAME: String? = null
        fun GetDeviceipMobileData(): String? {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val networkinterface = en.nextElement()
                    val enumIpAddr = networkinterface.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
//                      return inetAddress.getHostAddress().toString();
                            val ipA = Formatter.formatIpAddress(inetAddress.hashCode())
                            Log.i("IP Addr", "***** IP=$ipA")
                            return ipA
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("Current IP", ex.toString())
            }
            return null
        }

        fun GetDeviceipWiFiData(context: Context): String {
            val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        }

        fun isValidEmail(target: CharSequence): Boolean {
            val regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@([a-z]+\\.[a-z]+(\\.[a-z]+)?)"
            //      ([a-z0-9._%+-]+@[a-z0-9.-]+(\.[a-z]+)?)
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(target)
            println(target.toString() + " : " + matcher.matches())
            return !TextUtils.isEmpty(target) && matcher.matches()
        }
    }
}