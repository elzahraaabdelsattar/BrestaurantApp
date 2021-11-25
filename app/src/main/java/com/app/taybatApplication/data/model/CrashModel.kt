package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class CrashModel(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("crash")
    val crash: Int
)