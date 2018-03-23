package com.kakao.sdk.network

import retrofit2.Retrofit

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class DefaultOAuthService {
    private val retrofit = Retrofit.Builder().baseUrl("https://kauth.kakao.com/").build()

}