package com.app.taybatApplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.content_reservation.*
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        init()

        send_reservationActivity_button.setSafeOnClickListener {
            makeReservation()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun makeReservation() {

        reservation_progressBar.show()
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))
        val name=name_reservationActivity_editText.text.trim().toString()
        val phone=phone_reservationActivity_editText.text.trim().toString()

        //validation
        var valid = true

        if (name.isEmpty())
        {
            reservation_progressBar.hide()
            name_reservationActivity_editText.error = "Invalid Data"
            valid = false
        }
        if (phone.isEmpty())
        {
            reservation_progressBar.hide()
            phone_reservationActivity_editText.error = "Enter Your Password"
            valid = false
        }

        if (!valid) {
            return
        }

        RetrofitClient.instance.makeReservation(token,name,phone).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

                reservation_progressBar.hide()
                Toast.makeText(this@ReservationActivity,t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if (response.code()==200)
                {
                    if (response.body()!!.success)
                    {
                        reservation_progressBar.hide()
                        Toast.makeText(this@ReservationActivity,response.body()!!.data.toString(), Toast.LENGTH_LONG).show()
                    }

                }
                else{
                    reservation_progressBar.hide()
                    Toast.makeText(this@ReservationActivity,"Error In Data", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun init() {

        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="الحجوزات"
        toggle= ActionBarDrawerToggle(this,drawer_layout_reservation,R.string.open,R.string.close)
        drawer_layout_reservation.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_reservation.setNavigationItemSelectedListener(this);


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home->
            {
                navigateToFoodMenuActivity()
            }
            R.id.nav_bag->
            {
                navigateToBag()
            }
            R.id.nav_offers->
            {
                navigateToOffers()
            }
            R.id.nav_reservation->
            {
                navigateToReservation()
            }
            R.id.nav_myorders->
            {
                navigateToMyOrders()
            }
            R.id.nav_suggestion->
            {
                navigateToComplaint()
            }
            R.id.nav_review->
            {
                navigateToReviewActivity()
            }
            R.id.nav_about_us->
            {
                navigateToBoutUs()
            }
            R.id.nav_branches->
            {
                navigateToBranches()
            }
            R.id.nav_logout->
            {
                ModelConveter.logOut(this)
            }

        }
        drawer_layout_reservation.closeDrawer(GravityCompat.START)
        return true

    }
}