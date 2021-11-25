package com.app.taybatApplication.ui.foodMenu

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.CategoriesList
import com.app.taybatApplication.util.CallBack
import kotlinx.android.synthetic.main.items_main_menu.view.*

class FoodMenuAdapter(private var categoriesList: MutableList<CategoriesList>?, var context:Context, var callBack: CallBack)
    :RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>(){

    var rowIndex:Int=-1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_main_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category:CategoriesList= categoriesList!![position]

        holder.itemView.title_main_menu_item_textView.text=category.name
        
        holder.itemView.title_main_menu_item_textView.setOnClickListener{
                rowIndex=position
                notifyDataSetChanged()

                callBack.notifier(position,1)
                holder.itemView.title_main_menu_item_textView.isSelected=true
        }
//        if (rowIndex==position)
//        {
//           // holder.itemView.title_main_menu_item_textView.setBackgroundResource(R.drawable.yellow_color_rectangle);
//            holder.itemView.title_main_menu_item_textView.setTextColor(Color.parseColor("#ffffff"));
//        }
//       else
//        {
//           // holder.itemView.title_main_menu_item_textView.setBackgroundResource(R.drawable.back_border);
//            holder.itemView.title_main_menu_item_textView.setTextColor(Color.parseColor("#000000"));
//        }




    }

    override fun getItemCount(): Int
    {
        return categoriesList!!.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

