package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class Branch(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("longitude")
    val longitude: Double?,

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
)