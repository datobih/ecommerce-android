package com.example.e_commerceapp.repository

import android.content.SharedPreferences
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.Token
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.retrofit.EcommerceRetrofit
import com.example.e_commerceapp.retrofit.dto.*
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

    fun addProductToCart(tokenHeader: String,addToCartDTO: AddToCartDTO)=flow<DataState<Void?>>{
        emit(DataState.Loading())
        val response=ecommerceRetrofit.addProductToCart(tokenHeader,addToCartDTO).awaitResponse()
        if(response.isSuccessful) emit(DataState.Success(null))
        else emit(DataState.Error())


    }

    fun getCart(tokenHeader: String)=flow<DataState<List<OrderItemDTO>>>{

        emit(DataState.Loading())

        val response=ecommerceRetrofit.getCart(tokenHeader).awaitResponse()
        if(response.isSuccessful) emit(DataState.Success(response.body()))
        else emit(DataState.Error())
    }

    fun getPurchasedProducts(tokenHeader: String)=flow<DataState<List<OrderItemDTO>>>{

        emit(DataState.Loading())

        val response=ecommerceRetrofit.getPurchasedProducts(tokenHeader).awaitResponse()
        if(response.isSuccessful) emit(DataState.Success(response.body()))
        else emit(DataState.Error())
    }



    fun removeOrder(tokenHeader: String,pk:Int,itemPosition:Int)=flow<DataState<Int>>{
        emit(DataState.Loading())
        val response=ecommerceRetrofit.removeOrder(tokenHeader,pk).awaitResponse()
        if(response.isSuccessful) emit(DataState.Success(itemPosition))
        else emit(DataState.Error())

    }

    fun makePayment(tokenHeader: String,password:String)=flow<DataState<MakePaymentResponseDTO>>{
        emit(DataState.Loading())
        val response=ecommerceRetrofit.makePayment(tokenHeader,password).awaitResponse()

        if(response.isSuccessful) emit(DataState.Success(response.body()))
        else emit(DataState.Error())

    }

    fun topUpBalance(tokenHeader: String,topupDTO: TopupDTO)=flow<DataState<Void?>>{
        emit(DataState.Loading())

        val response=ecommerceRetrofit.topUpBalance(tokenHeader,topupDTO).awaitResponse()
        if(response.isSuccessful) emit((DataState.Success(null)))

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

    fun updateUserProfile(tokenHeader: String,fullname:String,
    email:String,address:String)=flow<DataState<Void?>>{
        emit(DataState.Loading())

        val response=ecommerceRetrofit.updateUserDetail(tokenHeader,fullname,
        email,address).awaitResponse()

        if(response.isSuccessful){
            emit(DataState.Success(null))
        }
        else emit(DataState.Error())


    }

    fun getUserToken(): String? {


        sharedPreferences.apply {

            return getString(Constants.SHARED_PREFERENCE_TOKEN_ACCESS, "")


        }

    }


}