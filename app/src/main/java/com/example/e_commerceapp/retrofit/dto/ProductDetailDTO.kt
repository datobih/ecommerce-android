package com.example.e_commerceapp.retrofit.dto

import com.google.gson.annotations.SerializedName

class ProductDetailDTO(
    val id:Int,
    val title: String,
    val description:String,
    val price: String,
    val images: List<String>,
    val discount:Int,
    val category: String,
    val vendor:String,
    @SerializedName("average_rating")
    val averageRating:Int,
    @SerializedName("rating_count")
    val ratingCount:Int


) {
}