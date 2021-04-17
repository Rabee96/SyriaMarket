package com.example.syriamarket.pojo.products

data class Products(
    val `data`: Data,
    val requestedAt: String,
    val results: Int,
    val status: String
)