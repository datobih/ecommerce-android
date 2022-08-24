package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.OrderItemDTO
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasedItemsViewModel @Inject constructor(
    val repository: MainRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){


    val purchasedItemsLiveData:MutableLiveData<DataState<List<OrderItemDTO>>> = MutableLiveData()

    fun getPurchasedProducts(tokenHeader: String){
        viewModelScope.launch {

            try{
                repository.getPurchasedProducts(tokenHeader).collect{
                        dataState->
                    purchasedItemsLiveData.value=dataState

                }
            }

            catch (e:Exception){
                purchasedItemsLiveData.value=DataState.Error()
            }
        }
    }


    fun getUserTokenHeader(): String? {


        return "Bearer ${repository.getUserToken()}"
    }
}