package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class MakeOrderDetailsHome (

    @SerializedName("notes")
    var notes: String?,

    @SerializedName("city_id")
    var cityId: Int?,

    @SerializedName("longitude")
    var longitude: String?,

    @SerializedName("latitude")
    var latitude: String?,

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