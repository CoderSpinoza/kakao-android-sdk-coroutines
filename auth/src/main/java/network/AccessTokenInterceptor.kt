package com.kakao.sdk.auth.network

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.model.AccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
@ExperimentalCoroutinesApi
class AccessTokenInterceptor(
    private val recentToken: BroadcastChannel<AccessToken> = AccessTokenRepo.instance.observe()
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request() as Request
        runBlocking {
            val token = recentToken.openSubscription().receive()
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer ${token.accessToken}")
                    .build()
//            recentToken.cancel()
        }
        return chain.proceed(request)
    }
}