package com.kakao.sdk.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class KakaoAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        request = request.newBuilder()
                .addHeader("KA", "sdk/1.10.0-SNAPSHOT os/android-27 lang/ko-KR origin/lMXltzn4zSwq0EhwLKAo+k0zhqI= device/PIXEL-2 android_pkg/com.kakao.sdk.sample app_ver/1.0")
                .build()
        return chain.proceed(request)
    }
}