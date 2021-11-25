package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class BranchDto(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("branch")
    val branch: MutableList<Branch>


)