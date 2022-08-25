package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.SignupDTO
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val repository: MainRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    val createUserLiveData:MutableLiveData<DataState<Void?>> = MutableLiveData()


    fun createUser(signupDTO: SignupDTO){

        viewModelScope.launch {

            try{
                repository.createUser(signupDTO).collect { dataState ->
                    createUserLiveData.value = dataState
                }
            }
            catch(e:Exception){
                createUserLiveData.value=DataState.Error()
            }


        }

    }


}