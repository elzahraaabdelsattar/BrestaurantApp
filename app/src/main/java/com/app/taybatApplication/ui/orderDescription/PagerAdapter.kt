package com.app.taybatApplication.ui.orderDescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.taybatApplication.data.model.DishesDataDetails


class PagerAdapter(supportFragmentManager:FragmentManager):FragmentPagerAdapter(supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val mFragmentList=ArrayList<Fragment>()
    private val mFragmentTitleList=ArrayList<String>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]

    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
    fun addFragment(fragment: Fragment,title:String,dishes: DishesDataDetails?){

        val bundle = Bundle()
        bundle.putParcelable("dishes",dishes)
        fragment.arguments=bundle
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    }




