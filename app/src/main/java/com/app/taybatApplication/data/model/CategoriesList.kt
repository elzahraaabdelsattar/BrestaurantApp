package com.app.taybatApplication.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesList (

    @SerializedName("id")
    var id: Int,

    @SerializedName("image")
    var image: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("created_at")
    var created_at: String,

    @SerializedName("updated_at")
    var updated_at: String,

    @SerializedName("Is_Selected")
     var isSelected : Boolean=false



)