package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class RegisterationResponse (

    @SerializedName("id")
    val id: Int,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String?,

    @SerializedName("token")
    val token: String
)
