package com.app.taybatApplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Size (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?
) : Parcelable