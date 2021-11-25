package com.app.srestaurantapplication.ui.foodMenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.CategoriesList
import com.app.srestaurantapplication.util.CallBack
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.items_main_menu.view.*

class FoodMenuAdapter(private var categoriesList: List<CategoriesList>?, var context:Context, var callBack: CallBack)
    :RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodMenuAdapter.ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_main_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodMenuAdapter.ViewHolder, position: Int) {

        val category:CategoriesList= categoriesList!![position]
        holder.itemView.title_main_menu_item_textView.text=category.name

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(category.image)
            .apply(requestOptions)
            .into(holder.itemView.main_menu_item_imageView)



        holder.itemView.container_itemsMainMenu.setOnClickListener{callBack.notifier()}




    }

    override fun getItemCount(): Int
    {
        return categoriesList!!.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

