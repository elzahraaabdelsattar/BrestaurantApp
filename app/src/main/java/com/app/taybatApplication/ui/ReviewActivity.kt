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
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.content_review.*
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="التقييم"

        ///
        toggle= ActionBarDrawerToggle(this,drawer_layout_review,R.string.open,R.string.close)
        drawer_layout_review.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_review.setNavigationItemSelectedListener(this);


        send_reviewActivity_button.setSafeOnClickListener {
            sendRate()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun sendRate() {

        review_progressBar.show()

        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))
        val rate=rating_bar.rating.toInt().toString()
        val email=editTextTextEmailAddress.text.trim().toString()
        val name=editTextTextPersonName.text.trim().toString()


        //validation
        var valid = true

        if (!email.isValidEmail())
        {
            review_progressBar.hide()
            editTextTextEmailAddress.error = "Email Is Required"
            valid = false
        }

        if (name.isEmpty())
        {
            review_progressBar.hide()
            editTextTextPersonName.error = "Name Is Required"
            valid = false
        }

        if (!valid) {
            return
        }

        RetrofitClient.instance.sendRate(token,name,email,rate).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

                review_progressBar.hide()
                Toast.makeText(this@ReviewActivity,t.message.toString(),Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if(response.code()==200)
                {
                    if(response.body()!!.success)
                    {
                        review_progressBar.hide()
                        Toast.makeText(this@ReviewActivity,response.body()!!.data,Toast.LENGTH_LONG).show()

                    }
                }
                else{
                    review_progressBar.hide()
                    Toast.makeText(this@ReviewActivity,"Error In Data", Toast.LENGTH_LONG).show()
                }

            }
        })

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
        drawer_layout_review.closeDrawer(GravityCompat.START)
        return true
    }


}
