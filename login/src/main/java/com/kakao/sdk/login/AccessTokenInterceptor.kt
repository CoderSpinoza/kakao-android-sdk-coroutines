package com.kakao.sdk.login

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class AccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        request = request.newBuilder()
                .addHeader("Authorization", "Bearer FUQWp8ci9y2151g_IaGn1pmFVosWfRQeDU-Ulwo8BZUAAAFiVAsp7A")
                .build()
        return chain.proceed(request)
    }
}