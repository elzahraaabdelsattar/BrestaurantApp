package com.app.taybatApplication.ui.orderDescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.DishesDataDetails
import kotlinx.android.synthetic.main.fragment_description.*


class DescriptionFragment : Fragment() {
   var dishes: DishesDataDetails?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        dishes=bundle!!.getParcelable("dishes")
        desc_fragmentDescription_textView.text = dishes!!.description

        ////video_view
        if ((dishes!!.video) != null)

        {
            video_view.visibility=View.VISIBLE
            val mediaController = MediaController(activity)
            video_view.setVideoPath(dishes!!.image_url.plus(dishes!!.video))
            video_view.setMediaController(mediaController)
        }

        if(dishes!!.calory== null)
        {
            callories_textView_fragmentDescription.text=""+"كالوري"
        }
        else{
            callories_textView_fragmentDescription.text=dishes!!.calory.toString()+"كالوري"

        }
        allergens_textView_fragmentDescription.text=dishes!!.allergens


    }

    override fun onResume() {
        super.onResume()

        video_view.start()
    }

}