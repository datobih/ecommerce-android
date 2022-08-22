package com.example.e_commerceapp.retrofit.dto

import com.google.gson.annotations.SerializedName

class OrderItemDTO(
    val id:Int,
    @SerializedName("product")
    val title:String,
    val price:String,
    val quantity:Int,
    val images:List<String>


) {
}