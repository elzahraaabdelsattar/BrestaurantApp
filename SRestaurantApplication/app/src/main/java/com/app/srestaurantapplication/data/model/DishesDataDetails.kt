package com.app.srestaurantapplication.data.model


import com.google.gson.annotations.SerializedName

data class DishesDataDetails(
    @SerializedName("category")
    val category: Category,

    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("deleted")
    val deleted: Int,

    @SerializedName("description")
    val description: Any,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("product_details")
    val productDetails: List<ProductDetail>,

    @SerializedName("updated_at")
    val updatedAt: String
)