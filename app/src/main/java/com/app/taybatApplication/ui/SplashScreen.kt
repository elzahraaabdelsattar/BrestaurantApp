package com.app.taybatApplication.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.util.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        splash_screen_image_view.alpha = 0f
        splash_screen_image_view.animate().setDuration(1000).alpha(1f).withEndAction {

            val prefManager = PrefManager(this)
            if (prefManager.retrieveString(TOKEN).equals("")) {
                navigateToRegistration()

            } else {
                navigateToFoodMenuActivity()


            }
        }


    }
}

