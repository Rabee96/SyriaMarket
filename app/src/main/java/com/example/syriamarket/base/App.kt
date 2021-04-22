package com.example.syriamarket.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.syriamarket.interfaces.ApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App: Application() {

    companion object{
        lateinit var token: String
        lateinit var sp: SharedPreferences
        fun getToken(activity: Activity):String{
            sp = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            token = sp.getString("token", "N/A")!!
            return  token
        }
        fun setToken(activity: Activity, token1: String):Boolean{
            sp = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("token",token1)
            token = token1
            return editor.commit()
        }
        fun getUserBalance():String{
            return sp.getString("balance", "N/A")!!
        }
        fun setUserBalance(balance: String) : Boolean{
            val editor = sp.edit()
            editor.putString("balance",balance)
            return editor.commit()
        }
        fun signedIn (token: String): Retrofit{
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                chain.proceed(newRequest)
            }.build()

            return Retrofit.Builder()
                    .client(client)
                    .baseUrl(AppConstants.SYRIA_MARKET_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }


        var gson: Gson = GsonBuilder()
                .setLenient()
                .create()
        var pubgApi: Retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.AS7AP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        /*TODO var smsHUBApi: Retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.SMS_HUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()*/
        var syriaMarketAPI: Retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.SYRIA_MARKET_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }


}