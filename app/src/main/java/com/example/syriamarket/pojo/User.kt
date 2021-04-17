package com.example.syriamarket.pojo

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data")
    val info: Data,
    val status: String,
    val token: String
)