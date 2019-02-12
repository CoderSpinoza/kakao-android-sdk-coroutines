package com.kakao.sdk.network.data

import com.kakao.sdk.network.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
object ApiFactory {
    val kapi: Retrofit = withKakaoAgent("${Constants.SCHEME}://${Constants.KAPI}",
            OkHttpClient.Builder()
                    .addInterceptor(AppKeyInterceptor()))

    fun withKakaoAgent(url: String, clientBuilder: OkHttpClient.Builder): Retrofit {
        clientBuilder.interceptors().add(0, KakaoAgentInterceptor())
        return withClient(url,
                clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        )
    }

    fun withClient(url: String, clientBuilder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
                .client(clientBuilder.build())
                .build()
    }
}