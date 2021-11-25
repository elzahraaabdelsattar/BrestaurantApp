package com.app.taybatApplication.ui.registeration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.hide
import com.app.taybatApplication.util.isValidEmail
import com.app.taybatApplication.util.show
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        send_forgetPasswordActivity_button.setOnClickListener {
            forgetPassword()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun forgetPassword() {
        forgetPassword_progressBar.show()
        val email= email_forgetPasswordActivity_editText.text.toString().trim()

        //validation
        var valid = true

        if (!email.isValidEmail()|| email.isEmpty())
        {
            forgetPassword_progressBar.hide()
            email_forgetPasswordActivity_editText.error = "Enter valid Email"
            valid = false
        }


        if (!valid) {
            return
        }

        RetrofitClient.instance.forgetPassword(email).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {
                forgetPassword_progressBar.hide()
                Toast.makeText(this@ForgetPasswordActivity,t.message.toString(), Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if (response.code() == 200) {

                    if(response.body()!!.success)
                    {
                        forgetPassword_progressBar.hide()
                        Toast.makeText(this@ForgetPasswordActivity,response.body()!!.data, Toast.LENGTH_LONG).show()

                    }

                }
                else{
                    forgetPassword_progressBar.hide()
                    Toast.makeText(this@ForgetPasswordActivity,"Error In Data".toString(),Toast.LENGTH_LONG).show()

                }
            }
        })

    }


}