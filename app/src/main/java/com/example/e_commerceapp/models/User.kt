package com.example.e_commerceapp.models

import com.google.gson.annotations.SerializedName

class User(
    val email: String,
    @SerializedName(value = "fullname")
    val fullname: String,
    @SerializedName(value = "profile_picture")
    val profilePicture: String,
    val address:String,
    @SerializedName(value = "account_balance")
    val accountBalance:String,
    @SerializedName(value = "is_active")
    val isActive:Boolean
    ) {
}