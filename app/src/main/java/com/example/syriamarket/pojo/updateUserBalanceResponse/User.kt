package com.example.syriamarket.pojo.updateUserBalanceResponse

data class User(
    val __v: Int,
    val _id: String,
    val address: String,
    val balance: Int,
    val email: String,
    val name: String,
    val phone: String,
    val role: String,
    val userType: String
)