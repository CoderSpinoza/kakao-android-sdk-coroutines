package com.kakao.sdk.login.data

import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.data.AppKeyInterceptor
import com.kakao.sdk.network.data.KakaoAgentInterceptor
import com.kakao.sdk.network.data.KakaoConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
object ApiService {
    val kapi: Retrofit = Retrofit.Builder().baseUrl("${Constants.SCHEME}://${Constants.KAPI}")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(KakaoConverterFactory())
            .client(object : OkHttpClient() {
                override fun interceptors(): MutableList<Interceptor> {
                    return mutableListOf(AccessTokenInterceptor(), KakaoAgentInterceptor(), HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            })
            .build()
    val kauth: Retrofit = Retrofit.Builder().baseUrl("${Constants.SCHEME}://${Constants.KAUTH}")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(KakaoConverterFactory())
            .client(object : OkHttpClient() {
                override fun interceptors(): MutableList<Interceptor> {
                    return mutableListOf(AppKeyInterceptor(), KakaoAgentInterceptor(), HttpLoggingInterceptor(), HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            })
            .build()
}