package com.example.e_commerceapp.utils

sealed class UserDataState<out R> {

    class UserAvailable<out T>(val data:T):UserDataState<T>()
    class AnonymousUser:UserDataState<Nothing>()
    class Loading:UserDataState<Nothing>()


}