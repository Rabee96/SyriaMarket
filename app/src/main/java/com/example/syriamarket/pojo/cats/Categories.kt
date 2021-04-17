package com.example.syriamarket.pojo.cats

data class Categories(
    val `data`: Data,
    val requestedAt: String,
    val results: Int,
    val status: String
)