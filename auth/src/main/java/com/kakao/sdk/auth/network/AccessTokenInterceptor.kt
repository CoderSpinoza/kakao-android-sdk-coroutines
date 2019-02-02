package com.kakao.sdk.auth.network

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.model.AccessToken
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class AccessTokenInterceptor(private val recentToken: Observable<AccessToken> = AccessTokenRepo.instance.observe()) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        val token = recentToken.blockingFirst().accessToken
        request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        return  chain.proceed(request)
    }
}