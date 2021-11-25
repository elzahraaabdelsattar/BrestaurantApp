package com.app.taybatApplication.data.remote

import com.app.taybatApplication.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIClient {

    //signIn
    @POST("login")
    @FormUrlEncoded
    fun signIn(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<ResponseDto<RegisterationResponse>?>

    //signUp
    @POST("register")
    @FormUrlEncoded
    fun signUp(
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("re_password") re_password: String,
        @Field("email") email: String
    ): Call<ResponseDto<RegisterationResponse?>?>

    //forgetPassword
    @POST("password/create")
    @FormUrlEncoded
    fun forgetPassword(
        @Field("email") email: String
    ): Call<ResponseDto<String>?>

    //reservation
    @POST("make_reservation")
    @FormUrlEncoded
    fun makeReservation(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("phone") email: String
    ): Call<ResponseDto<String>?>


    //sendS_C
    @POST("send_c_and_s")
    @FormUrlEncoded
    fun s_c(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("message") message: String,
        @Field("phone") phone: String
    ): Call<ResponseDto<String>?>

    //sendRate
    @POST("send_rate")
    @FormUrlEncoded
    fun sendRate(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("rate") rate: String
    ): Call<ResponseDto<String>?>

    //get_categories
    @GET("all_categories")
    fun getCategories(
        @Header("Authorization") token: String
    ): Call<ResponseDto<MutableList<CategoriesList>>>

    //get_all_products
    @POST("all_products")
    @FormUrlEncoded
    fun getAllProducts(
        @Header("Authorization") token: String,
        @Field("page") page: Int,
        @Field("category_id") category_id: Int
    ): Call<ResponseDto<DishesData>?>


    //get_cities
    @GET("all_cities")
    fun getCities(
        @Header("Authorization") token: String
    ): Call<ResponseDto<MutableList<CityModel>?>?>

    //order_price
    @POST("order_price")
    fun getOrderPrice(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body orderDetails: OrderDetails
    ): Call<ResponseDto<ResponseOrderPrice>?>

    //get_offers
    @GET("offers")
    fun getOffers(
        @Header("Authorization") token: String,

    ): Call<ResponseDto<ResponsOffers>?>

    //get_close_open
    @GET("close_open")
    fun getClosedOpen(
        @Header("Authorization") token: String
    ): Call<ResponseDto<Int>>

    //log_out
    @POST("logout")
    fun logOut(
        @Header("Authorization") token: String
    ): Call<ResponseDto<String>>

    //get_my_orders
    @GET("my_orders")
    fun myOrders(
        @Header("Authorization") token: String
    ): Call<ResponseDto<MutableList<ResponseMyOrders>?>?>

    @GET("branches")
    fun getAllBranches(@Header("Authorization") token: String
    ): Call<BranchDto>

    //make_order_toHome
    @POST("make_order")
    fun makeOrder(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body makeOrderDetailsHome: MakeOrderDetailsHome
    ): Call<ResponseDto<String>?>

    //make_order_toRestaurant
    @POST("make_order")
    fun makeOrderRestaurant(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body makeOrderDetailsRestaurant: MakeOrderDetailsRestaurant
    ): Call<ResponseDto<String>?>


    //crashApp
    @GET("crash")
    fun crashApp(
    ): Call<CrashModel>

    //get_my_orders
    @POST("cancel_order")
    @FormUrlEncoded
    fun cancelOrders(
        @Header("Authorization") token: String,
        @Field("id") id: Int
    ): Call<ResponseDto<String>>


}