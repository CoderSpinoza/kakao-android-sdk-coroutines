package com.kakao.sdk.network.data

import com.kakao.sdk.network.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
object ApiService {
    val kapi = Retrofit.Builder().baseUrl("${Constants.SCHEME}://${Constants.KAPI}")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(KakaoConverterFactory())
            .client(object : OkHttpClient() {
                override fun interceptors(): MutableList<Interceptor> {
                    return mutableListOf(AppKeyInterceptor(), KakaoAgentInterceptor(), HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            })
            .build() as Retrofit

    fun <T> createApi(url: HttpUrl, clazz: Class<T>): T {
        return Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
                .client(OkHttpClient())
                .build().create(clazz)
    }
}