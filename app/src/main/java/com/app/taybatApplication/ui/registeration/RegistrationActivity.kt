package com.app.taybatApplication.ui.registeration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.taybatApplication.R
import com.app.taybatApplication.util.navigateToSignIn
import com.app.taybatApplication.util.navigateToSignUp
import com.app.taybatApplication.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_registeration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        signIn_registrationActivity_button.setSafeOnClickListener {
            navigateToSignIn()
        }


        sign_up_registrationActivity_button.setSafeOnClickListener {
            navigateToSignUp()
        }


    }
    //closeApp When back pressed
    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}