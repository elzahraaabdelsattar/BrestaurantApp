package com.app.taybatApplication.ui.foodMenu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.BagModel
import com.app.taybatApplication.data.model.BagSharedPrefrences
import com.app.taybatApplication.data.model.DishesDataDetails
import com.app.taybatApplication.util.CallBack
import com.app.taybatApplication.util.ModelConveter
import com.app.taybatApplication.util.hide
import com.app.taybatApplication.util.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.items_details_food_menu.view.*

class DetailsFoodMenuAdapter (private var dishesDetailsList: MutableList<DishesDataDetails>?, var context: Context, var callBack: CallBack)
    : RecyclerView.Adapter<DetailsFoodMenuAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_details_food_menu, parent, false)
        return ViewHolder(
            view
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dishesDetails:DishesDataDetails=dishesDetailsList!![position]

        holder.itemView.title_items_details_food_menu_textView.text=dishesDetails.name
       if (!((ModelConveter.convertArabic(dishesDetails.price).equals(0.0))))
       {
           holder.itemView.price_items_details_food_menu.text=dishesDetails.price+"رس"
       }
        else
       {
          // holder.itemView.price_items_details_food_menu.text=dishesDetails.productDetails!![0].price.toString()
           holder.itemView.price_items_details_food_menu.hide()
           holder.itemView.bag_itemsDetailsFoodMenu_textView.hide()
       }

        val image=dishesDetails.image_url.plus(dishesDetails.image)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(image)
            .apply(requestOptions)
            .into(holder.itemView.image_items_details_food_menu_imageView)
             holder.itemView.container_itemsMainMenu.setOnClickListener{callBack.notifier( position,2)}

        holder.itemView.bag_itemsDetailsFoodMenu_textView.setSafeOnClickListener {
            val item= BagModel(dishesDetails.name,dishesDetails.image_url.plus(dishesDetails.image),(ModelConveter.convertArabic(dishesDetails.price)),1,1,dishesDetails.id)
            BagSharedPrefrences.setBagData(item)
            Toast.makeText(context,"تم اضاقة المنتج",Toast.LENGTH_LONG).show()
        }



    }

    override fun getItemCount(): Int {
        return dishesDetailsList!!.size

    }
    fun addpeople(data: List<DishesDataDetails>?) {
        for (d in data!!) {
            dishesDetailsList!!.add(d)
        }
        notifyDataSetChanged()
    }




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}