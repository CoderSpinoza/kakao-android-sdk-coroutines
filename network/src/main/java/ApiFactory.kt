package com.kakao.sdk.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.network.data.AppKeyInterceptor
import com.kakao.sdk.network.data.KakaoAgentInterceptor
import com.kakao.sdk.network.data.KakaoConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
object ApiFactory {
    val kapi by lazy {
        withKakaoAgent("${Constants.SCHEME}://${Constants.KAPI}",
                OkHttpClient.Builder()
                        .addInterceptor(AppKeyInterceptor()))
    }

    fun withKakaoAgent(url: String, clientBuilder: OkHttpClient.Builder): Retrofit {
        clientBuilder.interceptors().add(0, KakaoAgentInterceptor())
        return withClient(url,
                clientBuilder.addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
        )
    }

    fun withClient(url: String, clientBuilder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(KakaoGsonFactory.base))
                .addConverterFactory(KakaoConverterFactory())
                .client(clientBuilder.build())
                .build()
    }
}