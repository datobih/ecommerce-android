package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.MakePaymentResponseDTO
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    val repository: MainRepository,
    val savedStateHandle: SavedStateHandle
):ViewModel() {

    val makePaymentLiveData:MutableLiveData<DataState<MakePaymentResponseDTO>> = MutableLiveData()
    val validatePaymentLiveData:MutableLiveData<Response<Void>> = MutableLiveData()

    fun makePayment(tokenHeader:String,password:String){

        viewModelScope.launch {
            try {
                repository.makePayment(tokenHeader, password).collect { dataState ->
                    makePaymentLiveData.value = dataState

                }
            }
            catch (e:Exception){
                makePaymentLiveData.value=DataState.Error()
            }

        }


    }

    fun validatePayment(){

        viewModelScope.launch {
            validatePaymentLiveData.value=repository.validatePayment(getUserTokenHeader()!!)


        }

    }

    fun getUserTokenHeader(): String? {


        return "Bearer ${repository.getUserToken()}"
    }



}