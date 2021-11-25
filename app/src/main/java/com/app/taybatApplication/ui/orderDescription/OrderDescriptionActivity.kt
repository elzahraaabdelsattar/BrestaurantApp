package com.app.taybatApplication.ui.orderDescription

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.DishesDataDetails
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_description.*

class OrderDescriptionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_description)


         val dishes:DishesDataDetails? = Gson().fromJson(
            intent.getStringExtra("dishes"),
            DishesDataDetails::class.java
        )


       setUpTabs(dishes)
    }

    private fun setUpTabs(dishes:DishesDataDetails?) {
        //set viewpager adapter
        val adapter=PagerAdapter(supportFragmentManager)
        adapter.addFragment(OrdersFragment(),"الطلب",dishes)
        adapter.addFragment(DescriptionFragment(),"الوصف",dishes)

        //set activity title
        title_orderDescTextView.text=dishes!!.name

       // set activity Image
        val image=dishes.image_url.plus(dishes.image)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(this)
            .load(image)
            .apply(requestOptions)
            .into(orderDescImageView)
       // setUpOrderFragment()

       // setUpDescriptionFragment()
        view_pager.adapter=adapter
        tab_Layout.setupWithViewPager(view_pager)

    }



}