package com.app.srestaurantapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import kotlinx.android.synthetic.main.activity_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)





        send_reviewActivity_button.setOnClickListener {
            sendRate()
        }

    }

    private fun sendRate() {

        val rate=rating_bar.rating.toInt().toString()
        val email=editTextTextEmailAddress.text.trim().toString()
        val name=editTextTextPersonName.text.trim().toString()

        RetrofitClient.instance.sendRate(name,email,rate).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                Toast.makeText(this@ReviewActivity,response.body()!!.data,Toast.LENGTH_LONG).show()
            }
        })

    }


}
