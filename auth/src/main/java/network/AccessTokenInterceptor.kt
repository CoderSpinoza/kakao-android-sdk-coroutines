package com.kakao.sdk.auth.network

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.model.AccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class AccessTokenInterceptor(
        @Suppress("EXPERIMENTAL_API_USAGE") private val recentToken: ConflatedBroadcastChannel<AccessToken> =
                AccessTokenRepo.instance.observe()
) : Interceptor {
    @ExperimentalCoroutinesApi
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        val token = recentToken.value
        request = request.newBuilder()
                .addHeader("Authorization", "Bearer ${token.accessToken}")
                .build()
        return chain.proceed(request)
    }
}