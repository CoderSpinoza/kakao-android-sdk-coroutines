package com.kakao.sdk.login

import com.kakao.sdk.network.AppKeyInterceptor
import com.kakao.sdk.network.KakaoAgentInterceptor
import com.kakao.sdk.network.KakaoConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
class ApiService {
    companion object {
        val kapi: Retrofit = Retrofit.Builder().baseUrl("https://kapi.kakao.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
                .client(object : OkHttpClient() {
                    override fun interceptors(): MutableList<Interceptor> {
                        return mutableListOf(AccessTokenInterceptor(), KakaoAgentInterceptor())
                    }
                })
                .build()
        val kauth: Retrofit = Retrofit.Builder().baseUrl("https://kauth.kakao.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
                .client(object : OkHttpClient() {
                    override fun interceptors(): MutableList<Interceptor> {
                        return mutableListOf(AppKeyInterceptor(), KakaoAgentInterceptor())
                    }
                })
                .build()
    }
}