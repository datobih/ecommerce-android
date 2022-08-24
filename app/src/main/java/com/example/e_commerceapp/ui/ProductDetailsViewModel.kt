package com.example.e_commerceapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.dto.AddToCartDTO
import com.example.e_commerceapp.retrofit.dto.ProductDetailDTO
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    val repository: MainRepository,
    val savedStateHandle: SavedStateHandle

) : ViewModel() {

    val productdetailLiveData: MutableLiveData<DataState<ProductDetailDTO>> = MutableLiveData()
    val productAddCartLiveData:MutableLiveData<DataState<Void?>> = MutableLiveData()

    fun getProductDetail(tokenHeader: String, productId: Int) {
        viewModelScope.launch {

            try{
                repository.getProductDetail(tokenHeader, productId).collect { dataState ->
                    productdetailLiveData.value = dataState
                }
            }
            catch(e:Exception){
                productdetailLiveData.value=DataState.Error()
            }

        }
    }

    fun addProductToCart(tokenHeader: String,addToCartDTO: AddToCartDTO){
        viewModelScope.launch {
            try{
                repository.addProductToCart(tokenHeader,addToCartDTO).collect{
                        dataState->
                    productAddCartLiveData.value=dataState
                }
            }
            catch (e:Exception){
                productAddCartLiveData.value=DataState.Error()
            }



        }


    }


    fun getUserTokenHeader(): String? {


        return "Bearer ${repository.getUserToken()}"
    }


}