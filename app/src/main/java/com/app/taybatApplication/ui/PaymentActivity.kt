package com.app.taybatApplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.EMAIL
import com.app.taybatApplication.data.local.PRICE
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.BagSharedPrefrences
import com.app.taybatApplication.data.model.MakeOrderDetailsHome
import com.app.taybatApplication.data.model.MakeOrderDetailsRestaurant
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.navigateToConfirmationActivity
import com.app.taybatApplication.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_check.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.tool_bar_main.*
import com.urway.paymentlib.UrwayPayment
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

    var notes=""
    var randomNumber=""
    var deliverWay=""
    var rnds=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        init()


        send_paymentActivity_button.setSafeOnClickListener {
            val prefManager = PrefManager(this)
            deliverWay =prefManager.retrieveString("deliver_way")!!
            notes=notes_paymentActivity_editText.text.toString()


            val intSelectButton: Int = payment_radio_group!!.checkedRadioButtonId
            //var radioButton:RadioButton=findViewById(intSelectButton)
            if(intSelectButton==R.id.cash_radio_button)
            {
               if(deliverWay.equals("ToHome"))
               {
                   makeOrderToHome("0")
                   prefManager.clearPreferences()

               }else
               {
                   makeOrderToRestaurant("0")
               }


            }
            else if(intSelectButton==R.id.online_radio_button)
            {

                val email=prefManager.retrieveString(EMAIL)
                val price=prefManager.retrieveString(PRICE)

                val urwayPayment = UrwayPayment()
                urwayPayment.makepaymentService(price!!,this,"1","SAR","45.93.101.144",
                    "","","","","",email!!,"Al TAif","Al Taif"
                    ,"Saudi Arabia","26513","SA",createRandomNumber(),"A","","0")

            }


        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == 2) {
                try {
                    val prefManager = PrefManager(this)

                    if(deliverWay.equals("ToHome"))
                    {
                        makeOrderToHome("1")
                        prefManager.clearPreferences()
                    }else
                    {
                        makeOrderToRestaurant("1")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            else{
                Toast.makeText(this,"فشل في عملية الدفع",Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun makeOrderToHome(paymentMethod:String) {
        val prefManager = PrefManager(this)
        val token = "Bearer ".plus(prefManager.retrieveString(TOKEN))
        val cityId = prefManager.retrieveString("city_id")!!.toInt()

        RetrofitClient.instance.makeOrder(
            "application/json",
            token,
            MakeOrderDetailsHome(notes,cityId,prefManager.retrieveString("longitude"),prefManager.retrieveString("latitude"),
            paymentMethod ,"home",prefManager.retrieveIntList("Ids"),prefManager.retrieveIntList("Quantities"),
                prefManager.retrieveIntList("Main")
            )
        ).enqueue(object: Callback<ResponseDto<String>?>{
            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if (response.code()==200){
                    if(response.isSuccessful){
                      navigateToConfirmationActivity()
                        BagSharedPrefrences.Clear()
                    }
                    else{
                        Toast.makeText(this@PaymentActivity,response.body()!!.error.toString(),Toast.LENGTH_LONG).show()

                    }
                }

            }

            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {
                Toast.makeText(this@PaymentActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun makeOrderToRestaurant(paymentMethod : String) {


        val prefManager = PrefManager(this)
        val token = "Bearer ".plus(prefManager.retrieveString(TOKEN))


        RetrofitClient.instance.makeOrderRestaurant(
            "application/json",
            token,
            MakeOrderDetailsRestaurant(notes,
                paymentMethod ,"restaurant",prefManager.retrieveIntList("Ids"),prefManager.retrieveIntList("Quantities"),
                   prefManager.retrieveIntList("Main")
            )
        ).enqueue(object: Callback<ResponseDto<String>?>{
            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if (response.code()==200){
                    if(response.isSuccessful){
                        navigateToConfirmationActivity()
                        BagSharedPrefrences.Clear()
                    }
                    else{
                        Toast.makeText(this@PaymentActivity,response.body()!!.error.toString(),Toast.LENGTH_LONG).show()

                    }
                }

            }

            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {
                Toast.makeText(this@PaymentActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun createRandomNumber():String {
        for (i in 0..14)
        {
            rnds = (0..9).random()
            randomNumber += rnds.toString()

        }
        return randomNumber
    }

    private fun init() {
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="الدفع"




    }
}