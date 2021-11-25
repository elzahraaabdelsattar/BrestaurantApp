package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class CityModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("area_id")
    val area_id: Int,

    @SerializedName("created_at")
    var created_at: String,

    @SerializedName("updated_at")
    val updated_at: String,

    @SerializedName("area")
    val area: AreaModel
)