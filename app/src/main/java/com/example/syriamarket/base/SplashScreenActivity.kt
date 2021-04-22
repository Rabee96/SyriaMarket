package com.example.syriamarket.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import base.MainActivity
import com.example.syriamarket.R
import com.example.syriamarket.base.App.Companion.getToken
import com.example.syriamarket.base.App.Companion.setUserBalance
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.userInfo.UserInfo
import com.example.syriamarket.ui.auth.AuthActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        setContentView(R.layout.activity_splash_screen)
        getToken(this)
        Handler(Looper.getMainLooper()).postDelayed({
            if (token != "N/A")
                userSignedIn(token)
            else {
                val i = Intent(this@SplashScreenActivity, AuthActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 2500)
    }

    fun userSignedIn(token: String) {
        val userService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
        userService.getUser("Bearer $token").enqueue(object :
                Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("Rabee","Splash Auth response code: ${response.code()}")
                when(response.code()){
                    200 ->{
                        if (response.body() != null) {
                            val balance = response.body()!!.data.data.balance.toString()
                            setUserBalance(balance)
                            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    }
                    401 ->{
                        val i = Intent(this@SplashScreenActivity, AuthActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                userSignedIn(token)
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}