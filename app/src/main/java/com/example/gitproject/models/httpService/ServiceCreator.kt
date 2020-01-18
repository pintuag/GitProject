package com.example.gitproject.models.httpService

import android.content.Context
import android.util.Log
import com.example.gitproject.util.ApiConstants
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {
    /**
     * This function will return the service class for ApiServiceCreator
     */
    private fun <S> createService(serviceClass: Class<S>): S {
        val httpClientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        val okHttpClient = httpClientBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(ApiConstants.API_END_POINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = retrofitBuilder.client(okHttpClient).build()
        return retrofit.create(serviceClass)
    }


    fun createService(): Api {
        return createService<Api>(Api::class.java)
    }

}