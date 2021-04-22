package com.example.syriamarket.pojo.updateToCompletedResponse

data class Product(
    val _id: String,
    val category: Category,
    val priceA: Int,
    val priceB: Int,
    val priceC: Int,
    val title: String,
    val url: String
)