package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class ResponseOrderPrice (

    @SerializedName("shipping_fees")
    val shipping_fees: Double,

    @SerializedName("sub_total")
    val sub_total:Double,


    @SerializedName("total_price")
    val total_price:Double
)