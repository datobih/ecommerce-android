package com.example.e_commerceapp

import com.example.e_commerceapp.retrofit.dto.CurrencyRateDTO

object Constants {
    val SHARED_PREFERENCE_NAME = "com.example.e_commerceapp.sharedpreference"
    val SHARED_PREFERENCE_TOKEN_ACCESS = "SHARED_PREFERENCE_TOKEN_ACCESS"
    val SHARED_PREFERENCE_TOKEN_REFRESH = "SHARED_PREFERENCE_TOKEN_REFRESH"
    val SHARED_PREFERENCE_USER_DATA = "SHARED_PREFERENCE_USER_DATA"

    val INTENT_EXTRA_PRODUCT_ID = "INTENT_EXTRA_PRODUCT_ID"
    val INTENT_EXTRA_TOTAL_PRICE="INTENT_EXTRA_TOTAL_PRICE"
    val INTENT_SUCCESS_CART_RESULT_CODE=3

    val MAKE_PAYEMENT_RESPONSE_SUCCESS="Transaction successful"
    val MAKE_PAYMENT_RESPONSE_INVALID_PASSWORD="Invalid password"
    val MAKE_PAYMENT_RESPONSE_INSUFFICIENT_BALANCE="Insufficient balance"
    var currentRate:CurrencyRateDTO?=null


    fun formatPrice(price:String):String{
        val stringBuilderPrice=StringBuilder(price)
        var count=0
        for(i in price.lastIndex downTo  0){
            count++
            if(count%3==0 &&(i!=0 && i!=price.lastIndex)){
                count=0
                stringBuilderPrice.insert(i,",")
            }

        }
return stringBuilderPrice.toString()

    }

}