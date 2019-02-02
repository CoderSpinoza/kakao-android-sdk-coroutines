package com.kakao.sdk.auth.network

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
    val kapi: Retrofit = createRetrofitBuilder(Constants.KAPI)
            .client(object : OkHttpClient() {
                override fun interceptors(): MutableList<Interceptor> {
                    return mutableListOf(AccessTokenInterceptor(), KakaoAgentInterceptor(), HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            })
            .build()
    val kauth: Retrofit = createRetrofitBuilder(Constants.KAUTH)
            .client(object : OkHttpClient() {
                override fun interceptors(): MutableList<Interceptor> {
                    return mutableListOf(AppKeyInterceptor(), KakaoAgentInterceptor(), HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            })
            .build()

    private fun createRetrofitBuilder(host: String): Retrofit.Builder {
        return Retrofit.Builder().baseUrl("${Constants.SCHEME}://$host")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
    }

    fun kapiWithClient(clientBuilder: OkHttpClient.Builder): Retrofit {
        return createRetrofitBuilder(Constants.KAPI).client(
                clientBuilder
                        .addInterceptor(AccessTokenInterceptor())
                        .addInterceptor(KakaoAgentInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .build()
    }

    fun kauthWithClient(clientBuilder: OkHttpClient.Builder): Retrofit {
        return createRetrofitBuilder(Constants.KAUTH).client(
                clientBuilder
                        .addInterceptor(AppKeyInterceptor())
                        .addInterceptor(KakaoAgentInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
        ).build()
    }
}