package com.app.taybatApplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.taybatApplication.R
import com.app.taybatApplication.util.navigateToFoodMenuActivity
import com.app.taybatApplication.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        home_confirmationActivity_button.setSafeOnClickListener {

            navigateToFoodMenuActivity()
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}