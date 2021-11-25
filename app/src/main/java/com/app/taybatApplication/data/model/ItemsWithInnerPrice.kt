package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class ItemsWithInnerPrice (

    @SerializedName("dishes")
    var dishes: DishesDataDetails,

    @SerializedName("price")
    var price: Double
    )