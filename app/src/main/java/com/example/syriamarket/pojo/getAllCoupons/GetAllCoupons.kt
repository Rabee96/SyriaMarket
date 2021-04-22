package com.example.syriamarket.pojo.getAllCoupons

data class GetAllCoupons(
    val `data`: Data,
    val requestedAt: String,
    val results: Int,
    val status: String
)