package com.app.taybatApplication.ui.registeration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.EMAIL
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.RegisterationResponse
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_signUpActivity_button.setSafeOnClickListener {

            signUp()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun signUp() {

        //sign_up_progressBar.show()

        //getData
        val name= name_signUpActivity_editText.text.toString().trim()
        val phone= phone_signUpActivity_editText.text.toString().trim()
        val password= password_signUpActivity_editText.text.toString().trim()
        val confirmPassword= cpassword_SignUpActivity_editText.text.toString().trim()
        val email= email_SignUpActivity_editText.text.toString().trim()




        //validation
        var valid = true

        if (name.isEmpty())
        {
            sign_up_progressBar.hide()
            name_signUpActivity_editText.error = "من فضلك ادخل اسمك"
            valid = false
        }
        if (phone.isEmpty())
        {
            sign_up_progressBar.hide()
            phone_signUpActivity_editText.error = "من فضلك ادخل رقم الهاتف الخاص بك"
            valid = false
        }

        if (password.isEmpty())
        {
            sign_up_progressBar.hide()
            password_signUpActivity_editText.error = "من فضلك اجخل رقم المرور"
            valid = false
        }
        if (confirmPassword.isEmpty())
        {
            sign_up_progressBar.hide()
            cpassword_SignUpActivity_editText.error = "من فضلك اعد ادخال كلمه المرور"
            valid = false
        }
        if (!email.isValidEmail()|| email.isEmpty())
        {
            sign_up_progressBar.hide()
            email_SignUpActivity_editText.error = "من فضلك ادخل البريد الالكتروني الخاص بك"
            valid = false
        }

        if (!valid) {
            return
        }

        //callApi
        RetrofitClient.instance.signUp(name,phone,password,confirmPassword,email).enqueue(object:Callback<ResponseDto<RegisterationResponse?>?>{
            override fun onResponse(
                call: Call<ResponseDto<RegisterationResponse?>?>,
                response: Response<ResponseDto<RegisterationResponse?>?>
            ) {

                if (response.code() == 200) {

                    if(response.body()!!.success)
                    {
                        //saveToken
                        val prefManager= PrefManager(this@SignUpActivity)
                        prefManager.saveString(TOKEN,response.body()!!.data!!.token)
                        prefManager.saveString(EMAIL,email)

                        //      sign_up_progressBar.hide()
                        ///navigate
                        navigateToFoodMenuActivity()
                    }
                    else {
                        //       sign_in_progressBar.hide()
                        Toast.makeText(this@SignUpActivity,response.body()!!.error,Toast.LENGTH_LONG).show()

                    }

                }
                else{
                    //    sign_up_progressBar.hide()
                    Toast.makeText(this@SignUpActivity,"Error In Data",Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<ResponseDto<RegisterationResponse?>?>, t: Throwable) {

                Toast.makeText(this@SignUpActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }
}