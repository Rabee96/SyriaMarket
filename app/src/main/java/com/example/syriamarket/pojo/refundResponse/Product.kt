package com.example.syriamarket.pojo.refundResponse

data class Product(
    val _id: String,
    val category: Category,
    val priceA: Int,
    val priceB: Int,
    val priceC: Int,
    val title: String,
    val url: String
)