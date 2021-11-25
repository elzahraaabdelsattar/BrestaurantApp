package com.app.taybatApplication.ui.check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PRICE
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.BagSharedPrefrences
import com.app.taybatApplication.data.model.OrderDetails
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.model.ResponseOrderPrice
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.ui.bag.BagAdapter
import com.app.taybatApplication.util.hide
import com.app.taybatApplication.util.navigateToPaymentActivity
import com.app.taybatApplication.util.setSafeOnClickListener
import com.app.taybatApplication.util.show
import kotlinx.android.synthetic.main.activity_check.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckActivity : AppCompatActivity() {

    var idList: MutableList<Int> = mutableListOf()
    var quantityList: MutableList<Int> = mutableListOf()
    var mainProductList: MutableList<Int> = mutableListOf()
    var totalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        ////toolBar

        check_progressBar.show()
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="الفاتوره"

        val prefManager = PrefManager(this)
        ///////////
        payment_checkActivity_button.setSafeOnClickListener {
            navigateToPaymentActivity()
        }

        /////////////////////
        setCheck()

        //total items of bill
        total_orders_checkActivity_textView.text =
            BagSharedPrefrences.retreiveBagData().size.toString()

        ///////////////////////////

        val deliverWay =prefManager.retrieveString("deliver_way")


        ///total price of bill with delivery/without delivery
        if (deliverWay.equals("ToHome")) {
            for (i in 0 until BagSharedPrefrences.retreiveBagData().size) {
                idList.add(BagSharedPrefrences.retreiveBagData()[i].id)
                quantityList.add(BagSharedPrefrences.retreiveBagData()[i].counter)
                mainProductList.add(BagSharedPrefrences.retreiveBagData()[i].MainItem)

            }
            ///save lists

            prefManager.saveIntList("Ids",idList)
            prefManager.saveIntList("Quantities",quantityList)
            prefManager.saveIntList("Main",mainProductList)



            orderPrice()
        }
        else {
            for (i in 0 until BagSharedPrefrences.retreiveBagData().size )
            {
                totalPrice += (BagSharedPrefrences.retreiveBagData()[i].counter) * (BagSharedPrefrences.retreiveBagData()[i].price.toDouble())

            }
            total_bill_checkActivity_textView.text = totalPrice.toString()
            prefManager.saveString(PRICE,totalPrice.toString())
        }



    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun orderPrice() {

        val prefManager = PrefManager(this)
        val token = "Bearer ".plus(prefManager.retrieveString(TOKEN))
        val cityId = prefManager.retrieveString("city_id")


        RetrofitClient.instance.getOrderPrice(
            "application/json",
            token,
            OrderDetails(cityId.toString(), idList, quantityList, mainProductList)
        )
            .enqueue(object : Callback<ResponseDto<ResponseOrderPrice>?> {
                override fun onFailure(call: Call<ResponseDto<ResponseOrderPrice>?>, t: Throwable) {
                    Log.d("error", "error", t)
                }

                override fun onResponse(
                    call: Call<ResponseDto<ResponseOrderPrice>?>,
                    response: Response<ResponseDto<ResponseOrderPrice>?>
                ) {
                    if (response.code()==200){
                        if(response.isSuccessful){

                            check_progressBar.hide()

                            shipping_fees_checkActivity_linear_layout.visibility=View.VISIBLE
                            Price_checkActivity_textView.visibility=View.VISIBLE
                            PPrice_checkActivity_textView.visibility=View.VISIBLE
                            PPrice_checkActivity_textView.text=response.body()!!.data!!.sub_total.toString()
                            shipping_fees_checkActivity_textView.text=response.body()!!.data!!.shipping_fees.toString()
                            total_bill_checkActivity_textView.text=response.body()!!.data!!.total_price.toString()
                            prefManager.saveString(PRICE,response.body()!!.data!!.total_price.toString())
                        }
                    }
                }
            })

    }

    private fun setCheck() {

        ///prepare ordered items in bill
        check_progressBar.hide()

        val bagAdapter =
            BagAdapter(BagSharedPrefrences.retreiveBagData(), this, 1)

        rv_checkActivity.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
        rv_checkActivity.setHasFixedSize(true)
        rv_checkActivity.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        rv_checkActivity.adapter = bagAdapter
    }
}
