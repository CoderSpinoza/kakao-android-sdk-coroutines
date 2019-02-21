package com.kakao.sdk.auth.network

import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.ApiFactory
import com.kakao.sdk.network.data.AppKeyInterceptor
import com.kakao.sdk.network.data.KakaoAgentInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
object OAuthApiFactory {
    val kapi: Retrofit = withClient(OkHttpClient.Builder())

    val kauth: Retrofit = kauthWithClient(OkHttpClient.Builder())

    fun withClient(clientBuilder: OkHttpClient.Builder): Retrofit {
        clientBuilder.interceptors().add(0, AccessTokenInterceptor())
        return  ApiFactory.withKakaoAgent("${Constants.SCHEME}://${Constants.KAPI}",
                clientBuilder)
    }

    fun kauthWithClient(clientBuilder: OkHttpClient.Builder): Retrofit {
        clientBuilder.interceptors().add(0, KakaoAgentInterceptor())
        clientBuilder.interceptors().add(0, AppKeyInterceptor())
        return ApiFactory.withClient("${Constants.SCHEME}://${Constants.KAUTH}",
                clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)))
    }
}