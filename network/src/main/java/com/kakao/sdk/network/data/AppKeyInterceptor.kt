package com.kakao.sdk.network.data

import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
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
                .addHeader(Constants.AUTHORIZATION, String.format("KakaoAK %s", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)))
                .build()
        return chain.proceed(request)
    }
}