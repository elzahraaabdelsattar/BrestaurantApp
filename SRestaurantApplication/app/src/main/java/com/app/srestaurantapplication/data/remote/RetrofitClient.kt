package com.app.srestaurantapplication.data.remote

import com.app.srestaurantapplication.core.Const.API_HOST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


        val instance: APIClient by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(APIClient::class.java)
        }
    }
