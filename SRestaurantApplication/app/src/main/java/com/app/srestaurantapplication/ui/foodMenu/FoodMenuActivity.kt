package com.app.srestaurantapplication.ui.foodMenu

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.convertTo
import androidx.core.graphics.toColor
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.CategoriesList
import com.app.srestaurantapplication.data.model.ResponseDto
import com.app.srestaurantapplication.data.remote.RetrofitClient
import com.app.srestaurantapplication.util.CallBack
import com.app.srestaurantapplication.util.navigateToDetailsFoodMenuActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class FoodMenuActivity : AppCompatActivity(),CallBack,NavigationView.OnNavigationItemSelectedListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_menu)

        init()


    }
//
//    @SuppressLint("SimpleDateFormat")
//    private fun getClock() {
//
//        val answer: String
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val current = LocalDateTime.now()
//            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//              answer=  current.format(formatter)
//
//           // Log.d("answer",s.toString())
//        } else {
//            val date = Date()
//            val formatter = SimpleDateFormat("HH:mma")
//            answer = formatter.format(date)
//
//        }
////        if (s >=23 || s <=24)
////        {
////            toolbar.setTitle(R.string.afternoon)
////        }
//
//
//    }

    private fun init() {

        setSupportActionBar(findViewById(R.id.tool_bar))
        val toggle=ActionBarDrawerToggle(this,drawer_layout,tool_bar,0,0)
        //toggle.drawerArrowDrawable.color =R.color.yellow_color
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        getCategories()
        ///recyclerview
    }

    private fun getCategories() {
        RetrofitClient.instance.getCategories().enqueue(object:Callback<ResponseDto<MutableList<CategoriesList>?>?>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<CategoriesList>?>?>,
                t: Throwable
            ) {

            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<CategoriesList>?>?>,
                response: Response<ResponseDto<MutableList<CategoriesList>?>?>
            )
            {

                if (response.code()==200)
                {
                    if (response.body()!!.success)
                    {

                        val categoryList: MutableList<CategoriesList>? = response.body()?.data
                        val categoryAdapter = FoodMenuAdapter(categoryList,this@FoodMenuActivity,this@FoodMenuActivity)
                        //////
                        recycler_view_main.layoutManager = LinearLayoutManager(this@FoodMenuActivity, RecyclerView.VERTICAL, false)
                        recycler_view_main.setHasFixedSize(true)
                        recycler_view_main.addItemDecoration(DividerItemDecoration(this@FoodMenuActivity, DividerItemDecoration.VERTICAL))

                        recycler_view_main.adapter = categoryAdapter
                        //Toast.makeText(this@CategoriesActivity,response.body()!!.data!![0].created_at,Toast.LENGTH_LONG).show()

                    }

                        else{
                            Toast.makeText(this@FoodMenuActivity,response.body()?.error,Toast.LENGTH_LONG).show()
                        }
                    }

                }

        })
    }

    override fun notifier() {
        navigateToDetailsFoodMenuActivity()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return true
    }


}



