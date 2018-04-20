package com.kakao.sdk.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class AppKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        request = request.newBuilder()
                .addHeader(StringSet.HEADER_KEY_AUTH, String.format("KakaoAK %s", Utility.getMetadata(ApplicationProvider.application, StringSet.META_APP_KEY)))
                .build()
        return chain.proceed(request)
    }
}