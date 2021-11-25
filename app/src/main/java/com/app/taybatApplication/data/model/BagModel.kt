package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class BagModel (
    @SerializedName("name")
    val name: String?,

    @SerializedName("image")
    val image: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("counter")
    var counter: Int,

    @SerializedName("MainItem")
    val MainItem: Int,

    @SerializedName("id")
    val id: Int
)