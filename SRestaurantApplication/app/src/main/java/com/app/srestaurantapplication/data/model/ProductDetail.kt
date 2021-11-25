package com.app.srestaurantapplication.data.model


import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("image")
    val image: List<String>,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("size")
    val size: Any,

    @SerializedName("size_id")
    val sizeId: Any,

    @SerializedName("type")
    val type: Any,

    @SerializedName("type_id")
    val typeId: Any,

    @SerializedName("updated_at")
    val updatedAt: String
)