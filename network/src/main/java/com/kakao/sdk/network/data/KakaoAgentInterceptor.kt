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
class KakaoAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request

        val header = Utility.getKAHeader(ApplicationProvider.application)
        request = request.newBuilder()
                .addHeader(Constants.KA, header)
                .build()
        return chain.proceed(request)
    }
}