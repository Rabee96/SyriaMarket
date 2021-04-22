package com.example.syriamarket.pojo.userCreatedResponse

data class User(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val address: String,
    val balance: Int,
    val email: String,
    val name: String,
    val password: String,
    val phone: String,
    val role: String,
    val userType: String
)