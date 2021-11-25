package com.app.taybatApplication.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDetail(


    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val product_id: Int,

    @SerializedName("size_id")
    val size_id: Int,

    @SerializedName("type_id")
    val type_id: Int,

    @SerializedName("price")
    val price:String,

    @SerializedName("created_at")
    val created_at: String?,

    @SerializedName("updated_at")
    val updated_at: String?,


    @SerializedName("size")
    val size: Size?,


    @SerializedName("type")
    val type: Type?,

    @SerializedName("Is_Selected")
    var isSelected : Boolean=false




):Parcelable

