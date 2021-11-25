package com.app.taybatApplication.ui.bag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.size
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.model.BagSharedPrefrences
import com.app.taybatApplication.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_bag.*
import kotlinx.android.synthetic.main.content_bag.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.deliver_way.view.*
import kotlinx.android.synthetic.main.tool_bar_main.*

class Bag : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag)

        init()
          setBag()
        //deliver
        continue_bagActivity_button.setSafeOnClickListener {

            val prefManager = PrefManager(this)

            if(rv_bagActivity.size==0)
            {
                continue_bagActivity_button.isEnabled=false
                Toast.makeText(this,"من فضلك اختر صنف واحد علي الاقل",Toast.LENGTH_LONG).show()
            }
            else
            {
                      val bottomSheetDialog = BottomSheetDialog(
                this, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.deliver_way,
                findViewById<View>(R.id.deliver_way_container) as? ConstraintLayout
            )

            bottomSheetView.from_restaurant_DeliverWay_cardView.setSafeOnClickListener {
                prefManager.saveString("deliver_way","FromRestaurant")
                navigateToCheckActivity()
            }

            bottomSheetView.to_home_DeliverWay_cardView.setSafeOnClickListener {
                prefManager.saveString("deliver_way","ToHome")
                navigateToLocationActivity()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            }

        }




    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    private fun setBag() {
        val bagAdapter =
          BagAdapter(BagSharedPrefrences.retreiveBagData(),this,0)

        rv_bagActivity.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
       // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
        rv_bagActivity.setHasFixedSize(true)
        rv_bagActivity.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        rv_bagActivity.adapter = bagAdapter

    }

    private fun init() {


        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="الحقيبه"
        toggle= ActionBarDrawerToggle(this,drawer_layout_bag,R.string.open,R.string.close)
        drawer_layout_bag.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_bag.setNavigationItemSelectedListener(this);


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
        drawer_layout_bag.closeDrawer(GravityCompat.START)
        return true

    }
}