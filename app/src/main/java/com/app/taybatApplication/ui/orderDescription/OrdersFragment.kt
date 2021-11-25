package com.app.taybatApplication.ui.orderDescription

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.model.*
import com.app.taybatApplication.util.ModelConveter
import com.app.taybatApplication.util.hide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<OrderFragmentAdapter.ViewHolder>? = null
    var type:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_orders, container, false)

    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val prefManager= PrefManager(requireContext())
        val bundle = arguments
        val dishes: DishesDataDetails? = bundle!!.getParcelable("dishes")

        /////set_Product_Details
        if(ModelConveter.convertArabic(dishes!!.price)==0.0)
        {
            group_fragment_orders.visibility=View.VISIBLE

            recycler_view_orderFragment.apply {
                layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
                // set the custom adapter to the RecyclerView
                adapter = OrderFragmentAdapter(dishes.productDetails,dishes)
            }

        }
        else{
            group_fragment_orders.visibility=View.GONE

        }

        if (number_picker_orders_fragment != null) {
            number_picker_orders_fragment.minValue = 1
            number_picker_orders_fragment.maxValue = 20
            number_picker_orders_fragment.wrapSelectorWheel = true
            type=number_picker_orders_fragment.value.toString()


            number_picker_orders_fragment.setOnValueChangedListener { picker, oldVal, newVal ->
                type = newVal.toString()
            }
        }

        buy_now_orderFragment_button.setOnClickListener {

            if (!((ModelConveter.convertArabic(dishes.price).equals(0.0))))
            {
                val item=BagModel(dishes.name,dishes.image_url.plus(dishes.image),(ModelConveter.convertArabic(dishes.price)),type.toInt(),1,dishes.id)
                BagSharedPrefrences.setBagData(item)
                val snackBar =Snackbar.make(order_fragment_layout,"تمت الاضافه الي الحقيبه",Snackbar.LENGTH_LONG)
                snackBar.setBackgroundTint(R.color.app_main_color_brown)
                snackBar.show()
            }

            else
            {
                val listPrices: MutableList<PriceModel> = BagSharedPrefrences.retreiveListPrice()

                if (listPrices.isEmpty())
                {
                    Toast.makeText(activity,"من فصلك قم باختيار الصنف الذي تريد اضافته للحقيبه",Toast.LENGTH_LONG).show()
                }
                else{
                    for (i in 0 until listPrices.size)
                    {

                        val item=BagModel(dishes.name,dishes.image_url.plus(dishes.image),listPrices[i].price,type.toInt(),0,listPrices[i].id)
                        BagSharedPrefrences.setBagData(item)

                    }
                    val snackbar =Snackbar.make(order_fragment_layout,"تمت الاضافه الي الحقيبه",Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(R.color.app_main_color_brown)
                    snackbar.show()
                    listPrices.clear()

                    buy_now_orderFragment_button.isEnabled = false
                    buy_now_orderFragment_button.isClickable = false
                }

            }


        }





    }


}

