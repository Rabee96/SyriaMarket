package com.example.syriamarket.pojo

data class CreateUser(
    val address: String,
    val email: String,
    val name: String,
    val password: String,
    val passwordConfirm: String,
    val phone: String,
    val role: String,
    val userType: String
)