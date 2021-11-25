package com.app.taybatApplication.data.model


import com.google.gson.annotations.SerializedName

data class OffersModel(
    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("deleted")
    val deleted: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video")
    val video: Any
)