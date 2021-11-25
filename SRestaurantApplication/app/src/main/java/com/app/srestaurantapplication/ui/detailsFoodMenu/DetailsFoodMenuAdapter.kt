package com.app.srestaurantapplication.ui.detailsFoodMenu

import android.content.Context
import android.text.TextUtils.concat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.DishesDataDetails
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.items_details_food_menu.view.*

class DetailsFoodMenuAdapter (private var dishesDetailsList: List<DishesDataDetails>?, var context: Context)
    : RecyclerView.Adapter<DetailsFoodMenuAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsFoodMenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_details_food_menu, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: DetailsFoodMenuAdapter.ViewHolder, position: Int) {
        val dishes:DishesDataDetails=dishesDetailsList!![position]
        holder.itemView.food_type_itemsDetailsFoodMenu_textView.text=dishes.name

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        for (i in 0..dishesDetailsList!!.size)
        {
            holder.itemView.price_itemsDetailsFoodMenu_textView.text=dishes.productDetails[i].price
            Glide.with(context)
                .load(concat(dishes.productDetails[i].imageUrl,dishes.productDetails[i].image[i]))
                .apply(requestOptions)
                .into(holder.itemView.food_type_itemsDetailsFoodMenu_imageView)
        }
    }

    override fun getItemCount(): Int {
        return dishesDetailsList!!.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}