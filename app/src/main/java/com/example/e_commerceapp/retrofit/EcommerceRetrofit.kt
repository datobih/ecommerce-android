package com.example.e_commerceapp.retrofit

import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.Token
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.retrofit.dto.AddToCartDTO
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.retrofit.dto.ProductDetailDTO
import com.example.e_commerceapp.retrofit.dto.SignupDTO
import retrofit2.Call
import retrofit2.http.*

interface EcommerceRetrofit {


    @POST("account/login-user/")
    fun loginUser(@Body loginDTO: LoginDTO):Call<Token>



    @POST("account/create-user/")
    fun createUser(@Body signupDTO: SignupDTO):Call<Void>

    @GET("account/user-data/")
    fun getUserProfile(@Header("AUTHORIZATION") tokenVal:String):Call<User>

    @GET("products/all/")
    fun getProducts(@Header("AUTHORIZATION") tokenVal:String):Call<List<Product>>

    @GET("products/product-detail/{PRODUCT_ID}/")
    fun getProductDetail(@Header("AUTHORIZATION") tokenVal:String,
                         @Path("PRODUCT_ID") productId:Int):Call<ProductDetailDTO>


    @POST("products/add-item/")
    fun addProductToCart(@Header("AUTHORIZATION") tokenVal:String, @Body addToCartDTO: AddToCartDTO) :Call<Void>


}