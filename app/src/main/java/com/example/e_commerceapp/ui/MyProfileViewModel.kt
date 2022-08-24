package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.models.User
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    val repository: MainRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val profileLiveData: MutableLiveData<DataState<User>> = MutableLiveData()
    val updateUserLiveData: MutableLiveData<DataState<Void?>> = MutableLiveData()

    fun getUserProfile(tokenVal: String) {

        viewModelScope.launch {
            try {

                repository.getUserProfile(tokenVal).collect { dataState ->
                    profileLiveData.value = dataState
                }
            } catch (e: Exception) {
                profileLiveData.value = DataState.Error()
            }
        }
    }


    fun updateUserDetails(
        tokenHeader: String, fullname: String,
        email: String, address: String
    ) {

        viewModelScope.launch {
            try{
                repository.updateUserProfile(tokenHeader, fullname, email, address)
                    .collect { dataState ->
                        updateUserLiveData.value = dataState

                    }
            }
            catch (e:Exception){
                updateUserLiveData.value=DataState.Error()
            }


        }


    }

    fun getUserTokenHeader(): String? {


        return "Bearer ${repository.getUserToken()}"
    }


}