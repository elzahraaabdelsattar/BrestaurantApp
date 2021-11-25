package com.urway.paymentlib

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.DateTimePatternGenerator
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.webkit.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.net.Inet4Address
import java.net.URL
import java.net.URLDecoder
import java.net.UnknownHostException
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory

//{"result":"Successful","responseCode":"000","authcode":"235107","tranid":"2012913479070764088","trackid":"584277","terminalid":"iOSAndTerm","udf1":"","udf2":"","udf3":"","udf4":"","udf5":"","rrn":"012913235107","eci":"02","subscriptionId":null,"trandate":null,"tranType":null,"integrationModule":null,"integrationData":null,"payid":null,"targetUrl":null,"postData":null,"intUrl":null,"responseHash":"8b312c4988b5d43cf12747b07342f168e143805d54023659f1befdc80ae6d877","amount":"200.00","cardBrand":"MASTER"}
class Checkout : AppCompatActivity() {
    lateinit var mWeb: WebView
    lateinit var mProgress: ProgressDialog
    lateinit var txtLoad: TextView
     var weburl: String? = null
     var transId: String? = null
     var ResponseCode: String? = null
     private val hash = Sha1Encryption()
     val cancelpayId: String? = null
     var payId: String? = null
     var jsonstring: String? = null
     var cancelamt: String? = null
     var responseHash: String? = null
     var amount: String? = null
     var cardToken: String? = null
     var maskedCardNo: String? = null
     var merchantKey: String? = null
    var startweb = false
    var checkweb = false
    var UrwayPayment: UrwayPayment? = null
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // set webview as main content only
//        mWeb = new WebView(this);
        UrwayPayment = UrwayPayment()
        setContentView(R.layout.activity_checkout)
        mWeb = findViewById(R.id.webView1)
        context = this
        txtLoad = findViewById(R.id.txtload)
       txtLoad.setVisibility(View.GONE)
//      fun hacerVisibleLaFoto(v: View) {
         //txtLoad.visibility = View.GONE;
//      }
        if (savedInstanceState == null) {
            val extras = intent.extras
            println("Hello if weburl 1")
            if (extras == null) {
                weburl = null
                println("Hello if weburl=$weburl")
            } else {
                weburl = extras.getString("weburl")
                cancelamt = extras.getString("amt")
                payId = extras.getString("payid")
                jsonstring = extras.getString("jsonstring")
                println("Hello if weburl=$weburl")
            }
        } else {
            weburl = savedInstanceState.getSerializable("weburl") as String?
            println("Hello else weburl=$weburl")
        }

        if(!jsonstring.equals("")){
            System.out.println("res jsonstring***:"+jsonstring);
            val intentc = Intent()
            intentc.putExtra("MESSAGE", jsonstring)
            setResult(2, intentc)
            finish()
        }
        else{
        // set Javascript
        val settings = mWeb.getSettings()
        settings.javaScriptEnabled = true
        settings.defaultTextEncodingName = "utf-8"
        settings.domStorageEnabled = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        // the init state of progress dialog
        mProgress = ProgressDialog.show(this, "Loading", "Please wait...")
        mWeb.webViewClient= object : WebViewClient() {
       // mWeb.setWebViewClient(object : WebViewClient() {
            // load url
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (context is Activity) {
                    if (!(context as Activity).isFinishing) mProgress.show()
                }
                val request = Uri.parse(url)
                return if (url.contains("&Result=")) {
//                    if (mProgress.isShowing()) {
//                        mProgress.dismiss();
//                        System.out.println("Hello if weburl 3");
//                    }
//                          txtLoad.setVisibility(View.VISIBLE);
//                    Toast.makeText(Checkout.this, "Cannot Load URL", Toast.LENGTH_SHORT).show();
                    println("ur of shouldload =$url")
                    true
                } else {
//                    mProgress.show();
//                    txtLoad.setVisibility(View.VISIBLE);
                    view.loadUrl(url)
                    println(", url = $url")
                    //                    Toast.makeText(Checkout.this,"loading"+ url, Toast.LENGTH_SHORT).show();
                    false
                }
                //                Toast.makeText(Checkout.this,"loading" url, Toast.LENGTH_SHORT).show();
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
//                Toast.makeText(Checkout.this, "Error in Loading SSLData "+error.toString(), Toast.LENGTH_SHORT).show();
//                handler.proceed();
//                System.out.println("viewSSL = " + view + ", handler = " + handler + ", error = " + error);
                val builder = AlertDialog.Builder(this@Checkout)
                builder.setMessage("SSL Certificate Error")
                builder.setPositiveButton("Continue") { dialog, which -> handler.proceed() }
                builder.setNegativeButton("Cancel") { dialog, which -> handler.cancel() }
                val dialog = builder.create()
                dialog.show()
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                println("view = $view, request = $request, error = $error")
                //                Toast.makeText(Checkout.this, "Error in Loading Data"+error.toString(), Toast.LENGTH_SHORT).show();
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (context is Activity) {
                    if (!(context as Activity).isFinishing) mProgress.show()
                }

//                System.out.println("onpage start"+url);
//                if(url.contains("&Result=")) {
//                    mProgress.show();
////                    Toast.makeText(Checkout.this, "onPageLoadStart", Toast.LENGTH_SHORT).show();
////                    txtLoad.setVisibility(View.VISIBLE);
////                   finish();
//                }
//              Toast.makeText(Checkout.this, "start"+url, Toast.LENGTH_SHORT).show();
            }

            // when finish loading page
            override fun onPageFinished(view: WebView, url: String) {
                if (!url.contains("&Result=")) {
                    if (ResponseConfig.loadurl) {
                        if (mProgress != null && mProgress!!.isShowing) {            //changes by RUNALI for disable google  page
                            mProgress!!.dismiss()
                            //                            Toast.makeText(Checkout.this, "This is First URL", Toast.LENGTH_SHORT).show();
                            ResponseConfig.loadurl = false
                        }
                    } else {
                        if (mProgress != null && mProgress!!.isShowing) {
                            val handler = Handler()
                            handler.postDelayed({
                                //                                    if( Checkout.this!=null && !.isFinishing()) {
                                mProgress!!.dismiss()
                                //                                    }

//                                    Toast.makeText(Checkout.this, "finish url "+url, Toast.LENGTH_SHORT).show();
                            },
                                    2000)
                        }
                        println("OnPage finish$url")
                        //                        txtLoad.setVisibility(View.GONE);
                    }
                } else {
                    //      txtLoad.setVisibility(View.VISIBLE);
//                Toast.makeText(Checkout.this, "finish"+url, Toast.LENGTH_SHORT).show();
                    println("onPageFinished url=$url")
                    if (url.contains("cancelTransaction.htm")) //Added by Runali//ToDo changes for transaction cancelled by Customer
                    {
                        val intent = Intent()
                        ResponseConfig.startTrxn = false
                        ResponseConfig.loadurl = false
                        //                      DecimalFormat format = new DecimalFormat("##.##");
//                      String formatted = format.format(Double.parseDouble(cancelamt));
                        if (cancelamt!!.contains(".")) {
                            val valnumber = cancelamt!!.substring(cancelamt!!.indexOf(".")).substring(1)
                            if (valnumber.length == 1) {
                                cancelamt = cancelamt + "0"
                            }
                        } else {
                            cancelamt = "$cancelamt.00"
                        }
                        if (mProgress != null && mProgress!!.isShowing) {
                            mProgress!!.dismiss()
                            println("progress in cancel")
                        }
                        //                editText.setText(formatted);
//                    System.out.println("Data"+formatted);
                        generatedTrxnJson = generateTrxnRespJson(payId, "", cancelamt, "Cancelled Transaction", payId, cardToken) // enhncement
                        //                    UrwayPayment.DisplayJson(generatedTrxnJson);
                        ResponseConfig.finalRespJson = generatedTrxnJson
                        println(generatedTrxnJson.toString())
                        intent.putExtra("MESSAGE", generatedTrxnJson.toString())
                        setResult(2, intent)
                        finish() //finishing ac
                    }
                    if (url.contains("&Result=null")) //when all data is null
                    {
                        val intent = Intent()
                        ResponseConfig.startTrxn = false
                        ResponseConfig.loadurl = false
                        //                    DecimalFormat format = new DecimalFormat("##.##");
//                    String formatted = format.format(Double.parseDouble(amount));
//                editText.setText(formatted);
//                    System.out.println("Data"+formatted);
                        if (cancelamt!!.contains(".")) {
                            val valnumber = cancelamt!!.substring(cancelamt!!.indexOf(".")).substring(1)
                            if (valnumber.length == 1) {
                                cancelamt = cancelamt + "0"
                            }
                        } else {
                            cancelamt = "$cancelamt.00"
                        }
                        if (mProgress != null && mProgress!!.isShowing) {
                            mProgress!!.dismiss()
                            println("progress in null")
                        }
                        generatedTrxnJson = generateTrxnRespJson(payId, "", amount, "Failed Transaction", payId, cardToken)
                        //                    UrwayPayment.DisplayJson(generatedTrxnJson);
                        ResponseConfig.finalRespJson = generatedTrxnJson
                        println(generatedTrxnJson.toString())
                        intent.putExtra("MESSAGE", generatedTrxnJson.toString())
                        setResult(2, intent)
                        finish() //finishing ac
                    }

                    //toDo if Transaction gets all null data
                    if (url.contains("UnSuccessful")) {
                        val parameters = url.substring(url.indexOf("?") + 1).split("&").toTypedArray()
                        for (parameter in parameters) {
                            try {
                                val parts = parameter.split("=").toTypedArray()
                                val name = parts[0]
                                println("parameters[] name=$name")
                                if (name.equals("PaymentId", ignoreCase = true)) {
                                    println("name pay id $name")
                                    payId = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("responseHash", ignoreCase = true)) {
                                    println("name response hash $name")
                                    responseHash = parts[1]
                                    println("name response hash " + parts[1])
                                }
                                if (name.equals("ResponseCode", ignoreCase = true)) {
                                    println("name pay id $name")
                                    ResponseCode = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("TranId", ignoreCase = true)) {
                                    println("name pay id $name")
                                    transId = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("amount", ignoreCase = true)) {
                                    println("name pay id $name")
                                    amount = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("amount", ignoreCase = true)) {
                                    println("name pay id $name")
                                    amount = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("cardToken", ignoreCase = true)) {
                                    println("name pay id $name")
                                    cardToken = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("maskedCardPan", ignoreCase = true)) {
                                    println("name pay id $name")
                                    maskedCardNo = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (parts.size == 2) {
                                    val enValue = parts[1]
                                    println("parameters[] enValue=$enValue")
                                    val value = URLDecoder.decode(enValue, "UTF-8")
                                    println("parameters[] Value=$value")
                                    println("$name = $value")
                                } else {
                                    println("$name = ")
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace(System.err)
                            }
                        }
                        //Todo return method to UrwayPayment
                        //ToDo Code Here
                        if (mProgress != null && mProgress!!.isShowing) {
                            mProgress!!.dismiss()
                            println("Progress on unsucessful")
                        }
                        val intent = Intent()
                        ResponseConfig.startTrxn = false
                        ResponseConfig.loadurl = false
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

//                editText.setText(formatted);
                        println("Data$formatted")
                        generatedTrxnJson = generateTrxnRespJson(transId, ResponseCode, formatted, "UnSuccessful", payId, cardToken)
                        //                    UrwayPayment.DisplayJson(generatedTrxnJson);
                        println(generatedTrxnJson.toString())
                        intent.putExtra("MESSAGE", generatedTrxnJson.toString())
                        setResult(2, intent)
                        finish() //finishing ac
                    } else if (url.contains("Successful")) {
                        val parameters = url.substring(url.indexOf("?") + 1).split("&").toTypedArray()
                        for (parameter in parameters) {
                            try {
                                val parts = parameter.split("=").toTypedArray()
                                val name = parts[0]
                                println("parameters[] name=$name")
                                if (name.equals("PaymentId", ignoreCase = true)) {
                                    println("name pay id $name")
                                    payId = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("TranId", ignoreCase = true)) {
                                    println("name pay id $name")
                                    transId = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("amount", ignoreCase = true)) {
                                    println("name pay id $name")
                                    amount = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("ResponseCode", ignoreCase = true)) {
                                    println("name pay id $name")
                                    ResponseCode = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("responseHash", ignoreCase = true)) {
                                    println("name response hash $name")
                                    responseHash = parts[1]
                                    println("name response hash " + parts[1])
                                }
                                if (name.equals("cardToken", ignoreCase = true)) {
                                    println("name pay id $name")
                                    cardToken = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("maskedCardPan", ignoreCase = true)) {
                                    println("name pay id $name")
                                    maskedCardNo = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (parts.size == 2) {
                                    val enValue = parts[1]
                                    println("parameters[] enValue=$enValue")
                                    val value = URLDecoder.decode(enValue, "UTF-8")
                                    println("parameters[] Value=$value")
                                    println("$name = $value")
                                } else {
                                    println("$name = ")
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace(System.err)
                                println("exCEPTION = " + ex.message + ", url = " + url)
                            }
                        }
                        Log.e("res transId:", transId)
                        val generatedJson = generateResponseJson(transId, ResponseCode, amount)
                        ResponseConfig.finalRespJson = generatedTrxnJson
                        val ResponseHashValue = ConstantsVar.merchanKey?.let { generateResponseHashKey(generatedJson, it) }
                        println("mechant key$merchantKey")
                        Log.e("res ResponseHashValue:", ResponseHashValue)
                        Log.e("res responseHash:", responseHash)
                        //Todo sendFinalResponseData(generatedJson);
                        if (ResponseHashValue.equals(responseHash, ignoreCase = true)) {
//                        return method to UrwayPayment

//                        Intent intent = new Intent(getApplicationContext(), TransactionReceipt.class);
//                        intent.putExtra("payid", payId);
//                        intent.putExtra("transid", transId);
//                        intent.putExtra("amount", amount);
//                        intent.putExtra("result", "Successful");
//                        startActivity(intent);
////                        String message=data.getStringExtra("MESSAGE");
//                        finish();

                            //ToDo Code Here
//                editText.setText(formatted);
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
                            if (mProgress != null && mProgress!!.isShowing) {
                                mProgress!!.dismiss()
                                println("progess in sucess")
                            }
                            val intent = Intent()
                            ResponseConfig.startTrxn = false
                            ResponseConfig.loadurl = false
                            ResponseConfig.finalRespJson = generatedTrxnJson
                            generatedTrxnJson = generateTrxnRespJson(transId, ResponseCode, formatted, "Successful", payId, cardToken)
                            println(generatedJson.toString())
                            intent.putExtra("MESSAGE", generatedTrxnJson.toString())
                            setResult(2, intent)
                            finish() //finishing ac
                        } else {
//                        return method to UrwayPayment
//                        Intent intent = new Intent(getApplicationContext(), TransactionReceipt.class);
//                        intent.putExtra("payid", payId);
//                        intent.putExtra("transid", transId);
//                        intent.putExtra("amount", amount);
//                        intent.putExtra("result", "Invalid Transaction");
//                        startActivity(intent);
                            //ToDo Code Here
                            if (mProgress != null && mProgress!!.isShowing) {
                                mProgress!!.dismiss()
                                println("progess in in invalid")
                            }
                            val intent = Intent()
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
                            //                editText.setText(formatted);
                            println("Data$formatted")
                            ResponseConfig.startTrxn = false
                            ResponseConfig.loadurl = false
                            ResponseConfig.finalRespJson = generatedTrxnJson
                            generatedTrxnJson = generateTrxnRespJson(transId, ResponseCode, formatted, "Invalid Transaction", payId, cardToken)
                            println(generatedJson.toString())
                            intent.putExtra("MESSAGE", generatedTrxnJson.toString())
                            setResult(2, intent)
                            finish()
                        }
                    } else if (url.contains("Failure")) {
                        val parameters = url.substring(url.indexOf("?") + 1).split("&").toTypedArray()
                        for (parameter in parameters) {
                            try {
                                val parts = parameter.split("=").toTypedArray()
                                val name = parts[0]
                                println("parameters[] name=$name")
                                if (name.equals("PaymentId", ignoreCase = true)) {
                                    println("name pay id $name")
                                    payId = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("TranId", ignoreCase = true)) {
                                    println("name pay id $name")
                                    transId = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("ResponseCode", ignoreCase = true)) {
                                    println("name pay id $name")
                                    ResponseCode = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (name.equals("amount", ignoreCase = true)) {
                                    println("name pay id $name")
                                    amount = parts[1]
                                    println("name pay id " + parts[1])
                                }
                                if (parts.size == 2) {
                                    val enValue = parts[1]
                                    println("parameters[] enValue=$enValue")
                                    val value = URLDecoder.decode(enValue, "UTF-8")
                                    println("parameters[] Value=$value")
                                    println("$name = $value")
                                } else {
                                    println("$name = ")
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace(System.err)
                            }
                        }
                        //ToDo Code Here
                        ResponseConfig.startTrxn = false
                        ResponseConfig.loadurl = false
                        val intent = Intent()
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
                        if (mProgress != null && mProgress!!.isShowing) {
                            mProgress!!.dismiss()
                            println("progress in faile")
                        }
                        //                editText.setText(formatted);
                        println("Data$formatted")
                        generatedTrxnJson = generateTrxnRespJson(transId, ResponseCode, formatted, "Failed ", payId, cardToken)
                        ResponseConfig.finalRespJson = generatedTrxnJson
                        println(generatedTrxnJson.toString())
                        intent.putExtra("MESSAGE", generatedTrxnJson.toString())
                        setResult(2, intent)
                        finish() //finishing ac
                    }
                }
            }
        }
        mWeb.loadUrl(weburl)
        }
        //        Toast.makeText(this, "Loaded weburl"+weburl, Toast.LENGTH_SHORT).show();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun generateJson(terminal_Id: String?, terminal_Pass: String?, action_Code: String, Currency: String?, Amount: String?, customer_Ip: String?,
                     merchant_Ip: String?, usr_Fld1: String?, usr_Fld2: String?, usr_Fld3: String?, usr_Fld4: String?, usr_Fld5: String?, email: String?, address: String?, city: String?, state_code: String?, zip: String?, Country_Code: String? , merchantk: String?, track_Id: String?, cardOper: String, cardTok: String?, tokenType: String?): JSONObject {
        val testJson = JSONObject()
        try {
            testJson.put("terminalId", terminal_Id)
            testJson.put("password", terminal_Pass)
            testJson.put("action", action_Code)
            testJson.put("currency", Currency)
            if (email == null || "".equals(email, ignoreCase = true)) {
//                testJson.put("customerEmail", "a@test.com");//TODO Runali  Changes remove Test value
                testJson.put("customerEmail", "")
            } else {
                testJson.put("customerEmail", email)
            }
            testJson.put("country", Country_Code)
            testJson.put("amount", Amount)
            amount = Amount
            merchantKey = merchantk
            if (customer_Ip == null || "".equals(customer_Ip, ignoreCase = true)) {
                try {
                    testJson.put("customerIp", Inet4Address.getLocalHost().hostAddress)
                } catch (e: UnknownHostException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            } else {
                testJson.put("customerIp", customer_Ip)
            }
            if (merchant_Ip == null || "".equals(merchant_Ip, ignoreCase = true)) {
                try {
                    testJson.put("merchantIp", Inet4Address.getLocalHost().hostAddress)
                } catch (e: UnknownHostException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            } else {
                testJson.put("merchantIp", merchant_Ip)
            }
            /*
            if (("1").equalsIgnoreCase(action_Code) || ("4").equalsIgnoreCase(action_Code)) {
                if (trans_Id.length() > 18) {

                    testJson.put("transid", trans_Id);
                } else {

                    testJson.put("tranid", generateTranId());
                }
            } else {
                testJson.put("transid", trans_Id);
            }
            */
//            String trackID = getRandomNumberString();
//            Log.e("Response trackID:", trackID);
            testJson.put("trackid", track_Id)
            testJson.put("udf1", usr_Fld1)
            testJson.put("udf2", usr_Fld2)
            testJson.put("udf3", usr_Fld3)
            testJson.put("udf4", usr_Fld4)
            testJson.put("udf5", usr_Fld5)
            //          testJson.put("udf10", usr_Fld10);             //As per Sagar Changes
            testJson.put("udf7", "ANDROID")
            //            testJson.put("cardOperation", cardOper);

//            testJson.put("maskedCardNo", cardMsked);
            if (action_Code.equals("12", ignoreCase = true)) {
                testJson.put("tokenOperation", cardOper)
                if (cardOper.equals("U", ignoreCase = true)) {
//                    testJson.put("maskedPAN", cardMsked);
                    testJson.put("cardToken", cardTok)
                } else if (cardOper.equals("D", ignoreCase = true)) {
                    testJson.put("cardToken", cardTok)
                }
            } else if (action_Code.equals("1", ignoreCase = true) || action_Code.equals("4", ignoreCase = true)) {
                testJson.put("address", address)
                testJson.put("city", city)
                testJson.put("zipCode", zip)
                testJson.put("state", state_code)
                testJson.put("cardToken", cardTok)
                testJson.put("tokenizationType", tokenType)
            }
            else if (action_Code.equals("14", ignoreCase = true) ) {
                testJson.put("address", address)
                testJson.put("city", city)
                testJson.put("zipCode", zip)
                testJson.put("state", state_code)
                testJson.put("cardToken", cardTok)
                testJson.put("tokenizationType", tokenType)
            }

        } catch (e1: JSONException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        return testJson
    }

    //    Request Paramters after adding masked card
    //    {"terminalId":"iOSAndTerm","password":"password","action":"12","currency":"SAR","customerEmail":"sdsdh@gshd.jsdh","address":"","city":"","zipCode":"","country":"IN","amount":"10.00","state":"","customerIp":"227.17.36.227","merchantIp":"10.10.10.10","transid":"","trackid":"1233","udf1":"","udf2":"","udf3":"","udf4":"","udf5":"","udf7":"ANDROID","cardOperation":"A","cardToken":"","maskedCardNo":""}
    fun generateResponseJson(tranId: String?, responseCode: String?, amount: String?): JSONObject {
        val testJson = JSONObject()
        try {
            testJson.put("TranId", tranId)
            testJson.put("ResponseCode", responseCode)
            testJson.put("amount", amount)
        } catch (e1: JSONException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        //String json = testJson.toString();
        return testJson
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    val isConnectedInternet: Boolean get() {
        var connected = false
        try {
            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }

        return connected
    }

    fun generateHashKey(jsonObj: JSONObject, merchantKey: String?): String? {
        var pipeSeperatedString = ""
        var hashKey: String? = null
        try {
            try {
                pipeSeperatedString = jsonObj["trackid"].toString() + "|" + jsonObj["terminalId"] + "|" + jsonObj["password"] + "|" + merchantKey + "|" + jsonObj["amount"] + "|" + jsonObj["currency"]
                println("pipeSeperatedString$pipeSeperatedString")
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            hashKey = hash.SHA256(pipeSeperatedString)
        } catch (e: NoSuchAlgorithmException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return hashKey
    }

    fun generateResponseHashKey(jsonObj: JSONObject, merchantKey: String): String? {
        var pipeSeperatedString = ""
        var hashKey: String? = null
        try {
            try {
                println("Res TranId*******:" + jsonObj["TranId"])
                pipeSeperatedString = jsonObj["TranId"].toString() + "|" + merchantKey + "|" + jsonObj["ResponseCode"] + "|" + jsonObj["amount"]
                Log.e("Res pipeSeperatedString", pipeSeperatedString)
                println("Res pipeSeperatedString*******:$pipeSeperatedString")
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            hashKey = hash.SHA256(pipeSeperatedString)
        } catch (e: NoSuchAlgorithmException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return hashKey
    }

    fun generateTranId(): String? {
        var tranId: String? = null
        val dateFormat1 = SimpleDateFormat("yy")
        val dateFormat2 = SimpleDateFormat("HH")
        val c = GregorianCalendar.getInstance()
        try {
            c.clear(Calendar.DAY_OF_YEAR)
            val time = (Date().time.toString() + "").substring(0, 12)
            val hour = dateFormat2.format(Date())
            val year = dateFormat1.format(Date())
            val dayofYear = c[Calendar.DAY_OF_YEAR].toString() + ""
            tranId = year + dayofYear + hour + time
        } catch (e: Exception) {
            try {
                throw e
            } catch (e1: Exception) {
                e1.printStackTrace()
            }
        }
        return tranId
    }

    @Throws(Exception::class)
    fun sendTrans(requesturl: String, jsondata: JSONObject, hashValue: String, pgServiceReadtime: String?): String {
        val response = StringBuffer()
        val obj = URL(requesturl)
        println("configured url:$requesturl")
        jsondata.put("requestHash", hashValue)
        println("HashValue$hashValue")
        println("JSON REQ HASHVAL" + jsondata.getString("requestHash"))
        println("json request is$jsondata")
        /** */
        val sslSocketFactory: SSLSocketFactory
        sslSocketFactory = TLSSocketFactory()
        val connection = obj.openConnection() as HttpsURLConnection
        //        connection.setDefaultSSLSocketFactory(sslSocketFactory);
//        connection.setConnectTimeout((int) TimeUnit.MINUTES.toMillis(3));
//        connection.setReadTimeout((int) TimeUnit.MINUTES.toMillis(3));
        /** */
//        HttpURLConnection httpCon = (HttpURLConnection) obj.openConnection();
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json")
        connection.doOutput = true
        connection.doInput = true
        //        connection.connect();
        val out = OutputStreamWriter(connection.outputStream)
        out.write(jsondata.toString())
        out.flush()
        out.close()
        val `in` = BufferedReader(InputStreamReader(connection.inputStream))
        var inputLine: String?
        while (`in`.readLine().also { inputLine = it } != null) {
            println(inputLine)
            response.append(inputLine)
        }
        `in`.close()
        println("response string is$response")
        return response.toString()

//todo Getting Response Cde and doing the rest as said by Sagar
    }

    //    @Override  //As per Client requirement Saudi
    //    public void onBackPressed() {
    //        moveTaskToBack(false);
    //    }
    override fun onBackPressed() {
        super.onBackPressed()
        ResponseConfig.startTrxn = false
        ResponseConfig.loadurl = false
    }

    fun generateTrxnRespJson(tranId: String?, responseCode: String?, amount: String?, result: String?, payId: String?, cardToken: String?): JSONObject {
        val testJson = JSONObject()
        try {
            testJson.put("TranId", tranId)
            testJson.put("ResponseCode", responseCode)
            testJson.put("amount", amount)
            testJson.put("result", result)
            testJson.put("payId", payId)
            if (cardToken == null || cardToken.equals("null", ignoreCase = true)) {
                testJson.put("cardToken", "0")
            } else {
                testJson.put("cardToken", cardToken)
            }
        } catch (e1: JSONException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        //String json = testJson.toString();
        return testJson
    }

    fun standaloneRefundMethod(jsonString: String) {
        System.out.println("res jsonString:"+jsonString);
        val intentc = Intent()
        intentc.putExtra("MESSAGE", jsonString)
        setResult(2, intentc)
        finish() //finishing ac

    }

    companion object {
        var generatedTrxnJson: JSONObject? = null
        var initstartWeb = false

        // It will generate 6 digit random Number.
        // from 0 to 999999
        val randomNumberString:

                // this will convert any number sequence into 6 character.
                String
            get() {
                // It will generate 6 digit random Number.
                // from 0 to 999999
                val rnd = Random()
                val number = rnd.nextInt(999999)

                // this will convert any number sequence into 6 character.
                return String.format("%06d", number)
            }
    }
}