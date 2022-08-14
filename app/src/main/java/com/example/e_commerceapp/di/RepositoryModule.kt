package com.example.e_commerceapp.di

import android.content.SharedPreferences
import com.example.e_commerceapp.repository.MainRepository
import com.example.e_commerceapp.retrofit.EcommerceRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesMainRepository(ecommerceRetrofit: EcommerceRetrofit,
                               sharedPreferences: SharedPreferences):MainRepository{


        return MainRepository(ecommerceRetrofit,sharedPreferences)

    }


}