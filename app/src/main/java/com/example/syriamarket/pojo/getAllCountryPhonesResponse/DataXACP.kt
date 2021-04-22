package com.example.syriamarket.pojo.getAllCountryPhonesResponse

data class DataXACP(
    val _id: String,
    val cPhone: CPhone,
    val codeNumber: String,
    val country: Country,
    val createdAt: String,
    val isUsed: Boolean,
    val phoneNumber: String
)