package com.kakao.sdk.login.data

import com.kakao.sdk.login.domain.AccessTokenRepo
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class AccessTokenInterceptor(val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        request = request.newBuilder()
                .addHeader("Authorization", String.format("Bearer %s", accessTokenRepo.fromCache().accessToken))
                .build()
        return  chain.proceed(request)
    }
}