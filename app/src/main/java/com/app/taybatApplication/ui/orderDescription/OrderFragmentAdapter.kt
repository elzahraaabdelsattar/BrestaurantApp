package com.app.taybatApplication.ui.orderDescription


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.BagSharedPrefrences
import com.app.taybatApplication.data.model.DishesDataDetails
import com.app.taybatApplication.data.model.PriceModel
import com.app.taybatApplication.data.model.ProductDetail
import com.app.taybatApplication.util.ModelConveter
import com.app.taybatApplication.util.hide
import kotlinx.android.synthetic.main.items_fragment_order.view.*


class OrderFragmentAdapter (private var productDetails: List<ProductDetail>?,private val dishes:DishesDataDetails)
    : RecyclerView.Adapter<OrderFragmentAdapter.ViewHolder>() {

    var checkedPosition=0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_fragment_order, parent, false)
        return ViewHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val OproductDetails: ProductDetail = productDetails!![position]


        if (OproductDetails.size == null && OproductDetails.type == null) {

            holder.itemView.size_items_fragment_order_textView.hide()
            holder.itemView.type_items_fragment_order_textView.hide()
        }
        else if (OproductDetails.size == null) {
            holder.itemView.size_items_fragment_order_textView.hide()
            holder.itemView.type_items_fragment_order_textView.text = OproductDetails.type!!.name
        }
        else if (OproductDetails.type == null) {
            holder.itemView.type_items_fragment_order_textView.hide()
            holder.itemView.size_items_fragment_order_textView.text = OproductDetails.size.name

        } else {
            holder.itemView.size_items_fragment_order_textView.text = OproductDetails.size.name
            holder.itemView.type_items_fragment_order_textView.text = OproductDetails.type.name
        }

        holder.itemView.price_items_fragment_order_textView.text = OproductDetails.price



         holder.itemView.layout_container_items_fragment_order.setOnClickListener {

                 productDetails?.let{
                     it.forEach{it.isSelected = false}
                     it[position].isSelected = true
                     notifyDataSetChanged()
                 }

             //holder.itemView.checkmark_itemsFragmentOrder.visibility=View.GONE


             if (OproductDetails.isSelected )
               {
                    holder.itemView.checkmark_itemsFragmentOrder.visibility=View.VISIBLE
                    val item=PriceModel(OproductDetails.id, ModelConveter.convertArabic(OproductDetails.price))
                    BagSharedPrefrences.setListPrice(item)



               }
               else
               {
                   BagSharedPrefrences.retreiveListPrice().remove(BagSharedPrefrences.retreiveListPrice().find { it.id == OproductDetails.id })

               }

        }

    }


    override fun getItemCount(): Int {
            return productDetails!!.size

        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    }
