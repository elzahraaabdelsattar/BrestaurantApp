package com.app.taybatApplication.data.remote

import com.app.taybatApplication.core.Const.API_HOST
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {





    val instance: APIClient by lazy{
        var clientBuilder = OkHttpClient.Builder();
        var loggingInterceptor = HttpLoggingInterceptor();
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor);
            val retrofit = Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
        retrofit.create(APIClient::class.java)

        }


    }
