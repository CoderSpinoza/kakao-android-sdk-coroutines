package com.kakao.sdk.network.data

import com.kakao.sdk.common.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.common.Utility
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class AppKeyInterceptor(private val appKey: String = Utility.getMetadata(ApplicationProvider.application, com.kakao.sdk.common.Constants.META_APP_KEY)!!) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        request = request.newBuilder()
                .addHeader(Constants.AUTHORIZATION, "KakaoAK ${appKey}")
                .build()
        return chain.proceed(request)
    }
}