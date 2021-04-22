package com.example.syriamarket.pojo

data class CreateProduct(
    val category: String,
    val priceA: Double,
    val priceB: Double,
    val priceC: Double,
    val title: String,
    val url: String
)