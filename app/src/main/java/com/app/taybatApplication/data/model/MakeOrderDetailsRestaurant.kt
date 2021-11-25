package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class MakeOrderDetailsRestaurant (


    @SerializedName("notes")
    var notes: String?,

    @SerializedName("payment_method")
    var payment_method: String?,

    @SerializedName("delivered_place")
    var delivered_place: String?,

    @SerializedName("product_ids")
    var productIds: List<Int>?,

    @SerializedName("quantities")
    var quantities: List<Int>?,

    @SerializedName("main_product")
    var mainProduct: List<Int>?
        )