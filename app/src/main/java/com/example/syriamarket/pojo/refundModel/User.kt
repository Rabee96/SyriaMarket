package com.example.syriamarket.pojo.refundModel

data class User(
    val _id: String,
    val address: String,
    val balance: Int,
    val email: String,
    val name: String,
    val phone: String,
    val role: String,
    val userType: String
)