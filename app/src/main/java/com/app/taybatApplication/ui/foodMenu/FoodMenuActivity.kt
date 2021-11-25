package com.app.taybatApplication.ui.foodMenu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.*
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.ui.orderDescription.OrderDescriptionActivity
import com.app.taybatApplication.util.*
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_food_menu.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess


class FoodMenuActivity : AppCompatActivity(),CallBack,NavigationView.OnNavigationItemSelectedListener {


    lateinit var toggle:ActionBarDrawerToggle
    var dishesDetailsList:MutableList<DishesDataDetails>?=null
    var detailsFoodMenuAdapter:DetailsFoodMenuAdapter?=null
    var categoryList: MutableList<CategoriesList>?=null
    private var layoutManager: LinearLayoutManager? = null
    private var pageNumber = 1
    private var isLoading = true
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var previousTotal = 0
    private val viewThereshold = 8
    var token:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_menu)

        val prefManager= PrefManager(this)
        token="Bearer".plus(prefManager.retrieveString(TOKEN))
        recycler_view_detailsFoodMenu.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_detailsFoodMenu.layoutManager = layoutManager


        ///
        bag_contentMain_textView.setSafeOnClickListener {
            navigateToBag()
        }

        checkCrash()
        setOnScrollListener()

        ////
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        tool_bar.subtitle=""
        toggle= ActionBarDrawerToggle(this,drawer_layout,R.string.open,R.string.close)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white_color)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /////
    }
    //closeApp When back pressed
    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(a)
        exitProcess(0)



    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setOnScrollListener() {
        recycler_view_detailsFoodMenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager!!.childCount
                totalItemCount = layoutManager!!.itemCount
                pastVisibleItems = layoutManager!!.findFirstVisibleItemPosition()
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if (!isLoading && totalItemCount - visibleItemCount <= pastVisibleItems + viewThereshold) {
                        pageNumber++
                        PerformPagination(pageNumber)
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun checkCrash() {

        content_main_progressBar.show()

        RetrofitClient.instance.crashApp().enqueue(object:Callback<CrashModel>{
            override fun onResponse(call: Call<CrashModel>, response: Response<CrashModel>) {
                if(response.code()==200)
                {
                    if (response.body()!!.crash.toString()=="1")
                    {

                        getClosedOpen()

                    }
                    else {
                        5/0
                    }
                }



            }

            override fun onFailure(call: Call<CrashModel>, t: Throwable) {
                Toast.makeText(this@FoodMenuActivity,t.message,Toast.LENGTH_LONG).show()

            }
        })
    }


    private fun getClosedOpen() {
        RetrofitClient.instance.getClosedOpen(token).enqueue(object:Callback<ResponseDto<Int>>{
            override fun onFailure(call: Call<ResponseDto<Int>>, t: Throwable) {
                Toast.makeText(this@FoodMenuActivity,t.message,Toast.LENGTH_LONG).show()
            }

            @SuppressLint("ResourceType")
            override fun onResponse(
                call: Call<ResponseDto<Int>>,
                response: Response<ResponseDto<Int>>
            ) {
                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        if (response.body()!!.data==1 )
                        {
                            val mDialogView =
                LayoutInflater.from(this@FoodMenuActivity).inflate(R.layout.dialog_close_restaurant, null)
                 //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this@FoodMenuActivity)
                .setView(mDialogView)
                .setTitle("")
               //show dialog
               val mAlertDialog = mBuilder.show()
                mAlertDialog.window?.setLayout(500,700)
                           mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                        }
                        else
                        {
                            setSupportActionBar(findViewById(R.id.tool_bar))
                            nav_view.setNavigationItemSelectedListener(this@FoodMenuActivity)
                            getCategories()
                            getAllDishes(13,pageNumber)

                        }
                    }
                }
            }
        })
    }

    private fun getCategories() {



        RetrofitClient.instance.getCategories(token).enqueue(object:Callback<ResponseDto<MutableList<CategoriesList>>>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<CategoriesList>>>,
                t: Throwable
            ) {
                Toast.makeText(this@FoodMenuActivity,t.message.toString(), Toast.LENGTH_LONG).show()            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<CategoriesList>>>,
                response: Response<ResponseDto<MutableList<CategoriesList>>>
            ) {
                if (response.code()==200)
                {
                    if (response.body()!!.success)
                    {
                        categoryList= response.body()!!.data
                        val categoryAdapter = FoodMenuAdapter(categoryList,this@FoodMenuActivity,this@FoodMenuActivity)
                        //////


                        recycler_view_main.layoutManager = LinearLayoutManager(this@FoodMenuActivity, RecyclerView.HORIZONTAL, false)
                        recycler_view_main.setHasFixedSize(true)
                        recycler_view_main.addItemDecoration(DividerItemDecoration(this@FoodMenuActivity, DividerItemDecoration.VERTICAL))

                        recycler_view_main.adapter = categoryAdapter

                    }

                    else{

                        Toast.makeText(this@FoodMenuActivity,response.body()?.error,Toast.LENGTH_LONG).show()
                    }
                }            }
        })
    }


    private fun getAllDishes(categoryId:Int,pageNumber: Int) {

        content_main_progressBar.show()

        RetrofitClient.instance.getAllProducts(token,pageNumber,categoryId).enqueue(object:Callback<ResponseDto<DishesData>?>{
            override fun onFailure(call: Call<ResponseDto<DishesData>?>, t: Throwable) {
                content_main_progressBar.hide()
                Toast.makeText(this@FoodMenuActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<DishesData>?>,
                response: Response<ResponseDto<DishesData>?>
            ) {

                if (response.body()!!.success)
                {

                    content_main_progressBar.hide()

                    dishesDetailsList = response.body()!!.data!!.data
                    detailsFoodMenuAdapter= DetailsFoodMenuAdapter(dishesDetailsList,this@FoodMenuActivity,this@FoodMenuActivity)
                    recycler_view_detailsFoodMenu.adapter = detailsFoodMenuAdapter
                    //////


                    recycler_view_detailsFoodMenu.layoutManager = LinearLayoutManager(this@FoodMenuActivity, RecyclerView.VERTICAL, false)
                    // (recycler_view_main.layoutManager as LinearLayoutManager).reverseLayout = true
                    recycler_view_detailsFoodMenu.setHasFixedSize(true)
                    recycler_view_detailsFoodMenu.addItemDecoration(DividerItemDecoration(this@FoodMenuActivity, DividerItemDecoration.VERTICAL))

                    recycler_view_detailsFoodMenu.adapter = detailsFoodMenuAdapter

                }

                else{
                    content_main_progressBar.hide()
                    Toast.makeText(this@FoodMenuActivity,response.body()?.error,Toast.LENGTH_LONG).show()
                }            }
        })
    }

    private fun PerformPagination(pageNumber:Int) {
        content_main_progressBar.show()

        RetrofitClient.instance.getAllProducts(token,pageNumber,1).enqueue(object:Callback<ResponseDto<DishesData>?>{
            override fun onFailure(call: Call<ResponseDto<DishesData>?>, t: Throwable) {
                content_main_progressBar.hide()
                Toast.makeText(this@FoodMenuActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<DishesData>?>,
                response: Response<ResponseDto<DishesData>?>
            ) {

                if (response.body()!!.success)
                {

                    content_main_progressBar.hide()

                    dishesDetailsList = response.body()?.data!!.data
                    detailsFoodMenuAdapter!!.addpeople(dishesDetailsList)



                }

                else{
                    content_main_progressBar.hide()
                    Toast.makeText(this@FoodMenuActivity,response.body()?.error,Toast.LENGTH_LONG).show()
                }            }
        })

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
        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }



    override fun notifier(position: Int, flag: Int) {
        when(flag) {
            1 -> {
                getAllDishes(categoryList!![position].id,pageNumber)

            }

            2 -> {

                intent = Intent(this, OrderDescriptionActivity::class.java)
                intent.putExtra("dishes", Gson().toJson(dishesDetailsList!![position]))
                startActivity(intent)
            }
        }
    }


}



