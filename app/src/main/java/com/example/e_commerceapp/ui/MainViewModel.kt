package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.utils.DataState
import com.example.e_commerceapp.utils.UserDataState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userLiveData: MutableLiveData<UserDataState<String>> = MutableLiveData()
    val profileLiveData: MutableLiveData<DataState<User>> = MutableLiveData()
    val productsLiveData:MutableLiveData<DataState<List<Product>>> = MutableLiveData()

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
            repository.getProducts(tokenHeader).collect{
                    dataState->
                productsLiveData.value=dataState
            }
        }
    }

    fun getUserProfile(tokenVal:String){

        viewModelScope.launch {

            repository.getUserProfile(tokenVal).collect{
                dataState->
                profileLiveData.value=dataState
            }

        }


    }

    fun getUserTokenHeader(): String? {


       return "Bearer ${repository.getUserToken()}"
    }


}