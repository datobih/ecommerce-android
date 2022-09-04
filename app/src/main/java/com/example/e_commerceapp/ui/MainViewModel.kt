package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.CurrencyRateDTO
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.retrofit.dto.OrderItemDTO
import com.example.e_commerceapp.retrofit.dto.TopupDTO
import com.example.e_commerceapp.utils.DataState
import com.example.e_commerceapp.utils.UserDataState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.FieldPosition
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userLiveData: MutableLiveData<UserDataState<String>> = MutableLiveData()
    val profileLiveData: MutableLiveData<DataState<User>> = MutableLiveData()
    val productsLiveData:MutableLiveData<DataState<List<Product>>> = MutableLiveData()
    val cartItemsLiveData:MutableLiveData<DataState<List<OrderItemDTO>>> = MutableLiveData()
    val removeOrderLiveData:MutableLiveData<DataState<Int>> = MutableLiveData()
    val topUpLiveData:MutableLiveData<DataState<Void?>> = MutableLiveData()
    val currencyRateLiveData:MutableLiveData<DataState<CurrencyRateDTO>> = MutableLiveData()


    fun isUserLoggedIn() {
        viewModelScope.launch {

            repository.validateSession().collect {
                userLiveData.value = it
            }
        }
    }

    fun cacheUserData(user:User){

        with(repository.sharedPreferences.edit()){
            //Parse user data to JSON
            val userJson= Gson().toJson(user)
            putString(Constants.SHARED_PREFERENCE_USER_DATA,userJson)
            apply()

        }
    }

    fun getProducts(tokenHeader:String){

        viewModelScope.launch {

            try{
                repository.getProducts(tokenHeader).collect{
                        dataState->
                    productsLiveData.value=dataState
                }
            }
            catch (e:Exception){

                productsLiveData.value=DataState.Error()

            }

        }
    }

    fun getUserProfile(tokenVal:String){

        viewModelScope.launch {

            try{
                repository.getUserProfile(tokenVal).collect{
                        dataState->
                    profileLiveData.value=dataState
                }
            }
            catch (e:Exception){
                profileLiveData.value=DataState.Error()
            }

        }
    }





    fun getCart(tokenHeader: String){
        viewModelScope.launch {

            try {
                repository.getCart(tokenHeader).collect { dataState ->
                    cartItemsLiveData.value = dataState

                }
            }
            catch (e:Exception){
                cartItemsLiveData.value=DataState.Error()
            }
        }
    }

    fun getCurrencyRate(){

        viewModelScope.launch {
            try {
                repository.getCurrencyRate().collect(){
                    dataState->

                    currencyRateLiveData.value=dataState

                }

            }
            catch (e:Exception){
                currencyRateLiveData.value=DataState.Error()
            }


        }

    }


    fun removeOrder(tokenHeader: String,orderId:Int,itemPosition: Int){

        viewModelScope.launch {

            try{
            repository.removeOrder(tokenHeader,orderId,itemPosition).collect{
                dataState->
                removeOrderLiveData.value=dataState
            }
            }
            catch (e:Exception){
                removeOrderLiveData.value=DataState.Error()
            }

        }


    }

    fun topUpBalance(tokenHeader: String,topupDTO: TopupDTO){

        viewModelScope.launch {

            try{


            repository.topUpBalance(tokenHeader, topupDTO).collect{
                dataState->

                topUpLiveData.value=dataState

            }
            }
            catch (e:Exception){
                topUpLiveData.value=DataState.Error()
            }

        }


    }


    fun getUserTokenHeader(): String? {


       return "Bearer ${repository.getUserToken()}"
    }


}