package com.app.taybatApplication.ui.myOrders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.model.ResponseMyOrders
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_my_orders.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.content_my_orders.*
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrdersActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,CallBack {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        init()
        getMyOrders()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getMyOrders() {

        my_orders_progressBar.show()
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.myOrders(token).enqueue(object:Callback<ResponseDto<MutableList<ResponseMyOrders>?>?>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<ResponseMyOrders>?>?>,
                t: Throwable
            ) {
                my_orders_progressBar.hide()
                Toast.makeText(this@MyOrdersActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<ResponseMyOrders>?>?>,
                response: Response<ResponseDto<MutableList<ResponseMyOrders>?>?>
            )
            {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        my_orders_progressBar.hide()

                        val myOrdersAdapter =
                            MyOrdersAdapter(response.body()!!.data,this@MyOrdersActivity)

                        rv_myOrdersActivity.layoutManager = LinearLayoutManager(this@MyOrdersActivity, RecyclerView.VERTICAL, false)
                        // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
                        rv_myOrdersActivity.setHasFixedSize(true)
                        rv_myOrdersActivity.addItemDecoration(DividerItemDecoration(this@MyOrdersActivity, DividerItemDecoration.VERTICAL))

                        rv_myOrdersActivity.adapter = myOrdersAdapter
                    }
                    else{
                        my_orders_progressBar.hide()
                        Toast.makeText(this@MyOrdersActivity,"Error In Data",Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun init() {

        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="طلباتي"
        toggle= ActionBarDrawerToggle(this,drawer_layout_myOrders,R.string.open,R.string.close)
        drawer_layout_myOrders.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_myOrders.setNavigationItemSelectedListener(this);


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
        drawer_layout_myOrders.closeDrawer(GravityCompat.START)
        return true

    }

    override fun notifier(position: Int, flag: Int) {

        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.cancelOrders(token,position).enqueue(object:Callback<ResponseDto<String>>{
            override fun onResponse(
                call: Call<ResponseDto<String>>,
                response: Response<ResponseDto<String>>
            ) {
                if(response.code()==200)
                {
                    Toast.makeText(this@MyOrdersActivity,"تم الغاء طلبك",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {

                Toast.makeText(this@MyOrdersActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

}