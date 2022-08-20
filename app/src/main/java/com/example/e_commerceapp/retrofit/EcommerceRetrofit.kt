package com.example.e_commerceapp.retrofit

import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.Token
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.retrofit.dto.SignupDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EcommerceRetrofit {


    @POST("account/login-user/")
    fun loginUser(@Body loginDTO: LoginDTO):Call<Token>



    @POST("account/create-user/")
    fun createUser(@Body signupDTO: SignupDTO):Call<Void>

    @GET("account/user-data/")
    fun getUserProfile(@Header("AUTHORIZATION") tokenVal:String):Call<User>

    @GET("products/all/")
    fun getProducts(@Header("AUTHORIZATION") tokenVal:String):Call<List<Product>>


}