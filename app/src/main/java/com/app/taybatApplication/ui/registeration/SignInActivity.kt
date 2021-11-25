package com.app.taybatApplication.ui.registeration

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.EMAIL
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.RegisterationResponse
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.*
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        sign_in_signInActivity_button.setSafeOnClickListener {
            logIn()
        }

        fogot_passwrod_signInActivity_textView.setSafeOnClickListener {
            navigateToForgetPasswordActivity()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun logIn() {

        sign_in_progressBar.show()

        //getData
        val phone= phone_signInActivity_editText.text.toString().trim()
        val password= password_signInActivity_editText.text.toString().trim()

        //validation
        var valid = true

        if (password.isEmpty())
        {
            sign_in_progressBar.hide()
            password_signInActivity_editText.error = "من فضلك ادخل رقم المرور"
            valid = false
        }
        if (phone.isEmpty())
        {
            sign_in_progressBar.hide()
            phone_signInActivity_editText.error = "من فضلك ادخل رقم الهاتف"
            valid = false
        }

        if (!valid) {
            return
        }

        RetrofitClient.instance.signIn(phone,password).enqueue(object:Callback<ResponseDto<RegisterationResponse>?>{
            override fun onFailure(call: Call<ResponseDto<RegisterationResponse>?>, t: Throwable) {
                sign_in_progressBar.hide()
                Toast.makeText(this@SignInActivity,t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<RegisterationResponse>?>,
                response: Response<ResponseDto<RegisterationResponse>?>
            ) {
                if (response.code() == 200) {

                    if(response.body()!!.success)
                    {
                        //saveToken
                        val prefManager= PrefManager(this@SignInActivity)
                        prefManager.saveString(TOKEN,response.body()!!.data!!.token)
                        prefManager.saveString(EMAIL,response.body()!!.data!!.email)
                        sign_in_progressBar.hide()
                        updateFireBaseToken()
                        ///navigate
                        navigateToFoodMenuActivity()
                    }
                    else {
                        sign_in_progressBar.hide()
                        Toast.makeText(this@SignInActivity,response.body()!!.error,Toast.LENGTH_LONG).show()

                    }

                }

                else {
                    sign_in_progressBar.hide()
                    Toast.makeText(this@SignInActivity,"Error in data",Toast.LENGTH_LONG).show()

                }

            }
        })

    }

    private fun updateFireBaseToken() {


    }
}