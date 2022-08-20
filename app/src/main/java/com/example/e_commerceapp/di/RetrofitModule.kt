package com.example.e_commerceapp.di

import com.example.e_commerceapp.retrofit.EcommerceRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    @Singleton
    @Provides
    fun providesClient():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuild(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Singleton
    @Provides
    fun provideTodoService(retrofit: Retrofit): EcommerceRetrofit {

        return retrofit.create(EcommerceRetrofit::class.java)

    }


}