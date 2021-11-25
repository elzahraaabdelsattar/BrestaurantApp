package com.app.srestaurantapplication.data.remote

import com.app.srestaurantapplication.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIClient {

    //get_subjects

    //sendS_C
    @POST("send_c_and_s")
    @FormUrlEncoded
    fun s_c(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("message") message: String
    ): Call<ResponseDto<String>?>

    //get_categories
    @GET("all_categories")
    fun getCategories() :Call<ResponseDto<MutableList<CategoriesList>?>?>

    //get_all_products
    @POST("all_products")
    @FormUrlEncoded
    fun getAllProducts(
        @Field("page") page:String
    ): Call<ResponseDto<DishesData>>

    //sendS_C
    @POST("send_rate")
    @FormUrlEncoded
    fun sendRate(
        @Field("name") name:String,
        @Field("email") email: String,
        @Field("rate") rate: String
    ): Call<ResponseDto<String>?>



}