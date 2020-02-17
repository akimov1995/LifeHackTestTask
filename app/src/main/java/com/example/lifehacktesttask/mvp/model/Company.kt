package com.example.lifehacktesttask.mvp.model

data class Company(
    val id: String,
    val name: String,
    val img: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val www: String,
    val phone: String
)