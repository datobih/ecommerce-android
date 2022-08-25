package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(
    val repository: MainRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    val verifyOTPLiveData: MutableLiveData<DataState<Void?>> = MutableLiveData()


fun verifyOTP(otp:String){
    viewModelScope.launch {
        try {
           repository.verifyOTP(otp).collect { dataState ->

                    verifyOTPLiveData.value = dataState

            }
        }catch (e:Exception){
            verifyOTPLiveData.value=DataState.Error()
        }

    }


}



}