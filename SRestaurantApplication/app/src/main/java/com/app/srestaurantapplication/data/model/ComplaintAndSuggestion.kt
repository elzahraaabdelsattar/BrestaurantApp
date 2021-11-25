package com.app.srestaurantapplication.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ComplaintAndSuggestion (


    @SerializedName("success")
    @Expose
    var success: Boolean,

    @SerializedName("data")
    @Expose
    var data: String?

)