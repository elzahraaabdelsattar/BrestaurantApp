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
import kotlinx.android.synthetic.main.activity_complaints.*
import kotlinx.android.synthetic.main.content_complaints.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplaintsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaints)

        init()

        send_complaintActivity_button.setSafeOnClickListener {
            sendComplaint()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun sendComplaint() {

        s_c_progressBar.show()

        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))
        val name=name_complaintActivity_editText.text.toString()
        val email= email_complaintActivity_editText.text.toString()
        val message= complaints_complaintActivity_editText.text.toString().trim()
        val phone=phone_complaintActivity_editText.text.toString().trim()


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
        if (phone.isEmpty())
        {
            s_c_progressBar.hide()
            phone_complaintActivity_editText.error = "Phone Is Required"
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
        RetrofitClient.instance.s_c(token,name,email,message,phone).enqueue(object:Callback<ResponseDto<String>?>{
            override fun onFailure(call: Call<ResponseDto<String>?>, t: Throwable) {

                s_c_progressBar.hide()
                Toast.makeText(this@ComplaintsActivity,t.message.toString(),Toast.LENGTH_LONG).show()


            }

            override fun onResponse(
                call: Call<ResponseDto<String>?>,
                response: Response<ResponseDto<String>?>
            ) {
                if(response.code()==200){
                    if(response.body()!!.success)
                    {
                        s_c_progressBar.hide()
                        Toast.makeText(this@ComplaintsActivity,response.body()!!.data,Toast.LENGTH_LONG).show()

                    }
                    else{
                        s_c_progressBar.hide()
                        Toast.makeText(this@ComplaintsActivity,"Error In Data", Toast.LENGTH_LONG).show()

                    }
                }

            }
        })
    }


    private fun init() {

        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="الاقتراحات"
        toggle= ActionBarDrawerToggle(this,drawer_layout_complaint,R.string.open,R.string.close)
        drawer_layout_complaint.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_complaint.setNavigationItemSelectedListener(this);


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
        drawer_layout_complaint.closeDrawer(GravityCompat.START)
        return true
    }
}