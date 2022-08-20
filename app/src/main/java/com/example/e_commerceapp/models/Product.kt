package com.example.e_commerceapp.models

class Product(
    val id:Int,
    val title: String,
    val description:String,
    val price: String,
    val images: List<String>,
    val discount:Int,
    val category: String,
    val vendor:String
)