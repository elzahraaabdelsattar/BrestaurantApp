package com.app.taybatApplication.ui.myOrders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.ResponseMyOrders
import com.app.taybatApplication.util.CallBack
import com.app.taybatApplication.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_my_orders.view.*


class MyOrdersAdapter(private var myOrdersList: MutableList<ResponseMyOrders>?,var callBack: CallBack
)
    : RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>()


{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_orders, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyOrdersAdapter.ViewHolder, position: Int) {
        val OMyOrders: ResponseMyOrders =myOrdersList!![position]

        holder.itemView.serial_number_itemMyOrders_textView.text=OMyOrders.id
        holder.itemView.total_order_price_itemMyOrders_textView.text=OMyOrders.totalPrice
        holder.itemView.order_date_itemMyOrders_textView.text=OMyOrders.createdAt

        holder.itemView.remove_itemMyOrders_button.setSafeOnClickListener {

            callBack.notifier( OMyOrders.id.toInt(),0)
            myOrdersList!!.removeAt(position)

            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
        return myOrdersList!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}