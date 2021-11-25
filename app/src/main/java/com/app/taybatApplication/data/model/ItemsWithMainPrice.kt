package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class ItemsWithMainPrice (

    @SerializedName("dishes")
    var dishes: DishesDataDetails,

    @SerializedName("count")
    var count: Int,

    @SerializedName("Main_item")
    var Main_item: Int,

    @SerializedName("price")
    var price: List<Double>?
)