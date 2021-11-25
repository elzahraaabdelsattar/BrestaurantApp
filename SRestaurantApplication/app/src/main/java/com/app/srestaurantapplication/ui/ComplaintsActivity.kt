package com.app.srestaurantapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.ComplaintAndSuggestion
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.util.*
import kotlinx.android.synthetic.main.activity_complaints.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplaintsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaints)

        send_complaintActivity_button.setOnClickListener {

            //sendComplaint()
            navigateToFoodMenuActivity()

        }
    }

    private fun sendComplaint() {

        s_c_progressBar.show()
        val name=name_complaintActivity_editText.text.toString()
        val email= email_complaintActivity_editText.text.toString()
        val message= complaints_complaintActivity_editText.text.toString().trim()

        var valid = true

        if (!email.isValidEmail())
        {
            s_c_progressBar.hide()
            email_complaintActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (name.isEmpty())
        {
            s_c_progressBar.hide()
            name_complaintActivity_editText.error = "Name Is Required"
            valid = false
        }

        if (message.isEmpty())
        {
            s_c_progressBar.hide()
            complaints_complaintActivity_editText.error = "Please Provide Your Complaint Or Your Suggestion"
            valid = false
        }

        if (!valid) {
            return
        }
        RetrofitClient.instance.s_c(name,email,message).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                Toast.makeText(this@ComplaintsActivity,response.body()!!.data,Toast.LENGTH_LONG).show()

            }
        })
    }
}