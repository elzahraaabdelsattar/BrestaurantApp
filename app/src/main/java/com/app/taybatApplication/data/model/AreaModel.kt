package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class AreaModel (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("shipping_fees")
    val shipping_fees: Int

)