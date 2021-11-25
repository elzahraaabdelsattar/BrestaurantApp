package com.app.taybatApplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.app.taybatApplication.R
import com.app.taybatApplication.util.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.tool_bar_main.*

class AboutUs : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        init()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun init() {
        ////
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="عن المطعم"
        toggle= ActionBarDrawerToggle(this,drawer_layout_about,R.string.open,R.string.close)
        drawer_layout_about.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_about.setNavigationItemSelectedListener(this);


        /////
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
        drawer_layout_about.closeDrawer(GravityCompat.START)
            return true

        }
    }


