package com.example.e_commerceapp.repository

import android.content.SharedPreferences
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.Token
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.retrofit.EcommerceRetrofit
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.retrofit.dto.ProductDetailDTO
import com.example.e_commerceapp.utils.DataState
import com.example.e_commerceapp.utils.UserDataState
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class MainRepository(
    val ecommerceRetrofit: EcommerceRetrofit,
    val sharedPreferences: SharedPreferences
) {

    fun validateSession() = flow<UserDataState<String>> {
        emit(UserDataState.Loading())
        val accessToken = getUserToken()
        if (accessToken.isNullOrEmpty()) emit(UserDataState.AnonymousUser())
        else {
            emit(UserDataState.UserAvailable(accessToken))
        }

    }

    fun loginUser(loginDTO: LoginDTO) = flow<DataState<Token>> {
        emit(DataState.Loading())

        val response = ecommerceRetrofit.loginUser(loginDTO).awaitResponse()

        if (response.isSuccessful) {
            emit(DataState.Success(response.body()!!))
        } else {
            emit(DataState.Error())
        }

    }

    fun getProducts(tokenHeader: String)= flow<DataState<List<Product>>> {
        emit(DataState.Loading())
        val response=ecommerceRetrofit.getProducts(tokenHeader).awaitResponse()
        if(response.isSuccessful){
            emit(DataState.Success(response.body()))
        }
        else{
            emit(DataState.Error())
        }
    }

    fun getProductDetail(tokenHeader: String,productId:Int)=flow<DataState<ProductDetailDTO>>{
        emit(DataState.Loading())
        val response=ecommerceRetrofit.getProductDetail(tokenHeader,productId).awaitResponse()
        if(response.isSuccessful) emit(DataState.Success(response.body()))
        else emit(DataState.Error())



    }

    fun getUserProfile(tokenVal:String)= flow<DataState<User>> {
        emit(DataState.Loading())

        val response=ecommerceRetrofit.getUserProfile(tokenVal).awaitResponse()
        if(response.isSuccessful){
            emit(DataState.Success(response.body()))
        }
        else{
            emit(DataState.Error())
        }


    }


    fun getUserToken(): String? {


        sharedPreferences.apply {

            return getString(Constants.SHARED_PREFERENCE_TOKEN_ACCESS, "")


        }

    }


}