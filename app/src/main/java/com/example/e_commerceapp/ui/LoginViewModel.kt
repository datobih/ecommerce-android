package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.models.Token
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: MainRepository,
    val savedStateHandle: SavedStateHandle
):ViewModel() {


    val loginLiveData:MutableLiveData<DataState<Token>> = MutableLiveData()

    fun loginUser(loginDTO: LoginDTO) {
        viewModelScope.launch {
            try {
                repository.loginUser(loginDTO).collect { datastate ->
                    loginLiveData.value=datastate
                }
            }
            catch (e:Exception){
                loginLiveData.value=DataState.Error()
            }


        }

    }


    fun cacheToken(token: Token){

        repository.sharedPreferences.edit().apply{

            putString(Constants.SHARED_PREFERENCE_TOKEN_ACCESS,token.access)
            putString(Constants.SHARED_PREFERENCE_TOKEN_REFRESH,token.refresh)
            apply()

        }


    }

}