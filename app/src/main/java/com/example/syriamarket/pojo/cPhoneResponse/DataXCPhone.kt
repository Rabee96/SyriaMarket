package com.example.syriamarket.pojo.cPhoneResponse

data class DataXCPhone(
    val _id: String,
    val cPhone: CPhone,
    val codeNumber: String,
    val country: Country,
    val createdAt: String,
    val isUsed: Boolean,
    val phoneNumber: String
)