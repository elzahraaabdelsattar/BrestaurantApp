package com.app.taybatApplication.data.model


import com.google.gson.annotations.SerializedName

data class OrderDetails(
    @SerializedName("city_id")
    var cityId: String?,
    @SerializedName("product_ids")
    var productIds: List<Int>?,
    @SerializedName("quantities")
    var quantities: List<Int>?,
    @SerializedName("main_product")
    var mainProduct: List<Int>?



)