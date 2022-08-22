package com.example.e_commerceapp.utils

import java.text.FieldPosition

interface CartItemOnClickListener {
    fun onClick(orderID:Int,itemPosition: Int)
}