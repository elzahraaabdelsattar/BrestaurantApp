package com.app.taybatApplication.ui.offers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.model.BagModel
import com.app.taybatApplication.data.model.BagSharedPrefrences
import com.app.taybatApplication.data.model.OffersModel
import com.app.taybatApplication.util.ModelConveter
import com.app.taybatApplication.util.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.items_offers.view.*

class OffersAdapter (private var offersList: List<OffersModel>, val context: Context)
    : RecyclerView.Adapter<OffersAdapter.ViewHolder>()


{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_offers, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val offer:OffersModel=offersList[position]

        holder.itemView.title_offer_item_textView.text=offer.name.plus(offer.description)

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(offer.imageUrl.plus(offer.image))
            .apply(requestOptions)
            .into(holder.itemView.offer_item_imageView)

        holder.itemView.setSafeOnClickListener {

            val item= BagModel(offer.name,offer.imageUrl.plus(offer.image),(ModelConveter.convertArabic(offer.price)),1,1,offer.id)
            BagSharedPrefrences.setBagData(item)
            Toast.makeText(context,"تم اضاقة المنتج الي الحقيبه", Toast.LENGTH_LONG).show()
        }
    }


    override fun getItemCount(): Int {
        return offersList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)






}