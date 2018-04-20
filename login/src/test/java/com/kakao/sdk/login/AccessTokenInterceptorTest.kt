package com.kakao.sdk.login

import com.kakao.sdk.login.data.AccessTokenInterceptor
import com.kakao.sdk.network.StringSet
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
@RunWith(RobolectricTestRunner::class)
class AccessTokenInterceptorTest {
    lateinit var interceptor: AccessTokenInterceptor

    @Before
    fun setup() {
        ShadowLog.stream = System.out
        interceptor = AccessTokenInterceptor(Observable.just("access_token"))
    }

    @Test
    fun interceptor() {
        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        val server = MockWebServer()
        server.start()

        server.enqueue(MockResponse())
        client.newCall(Request.Builder().url(server.url("/")).build()).execute()
        val request = server.takeRequest()

        assertEquals(String.format("%s %s", StringSet.HEADER_PREFIX_BEARER, "access_token"), request.getHeader(StringSet.HEADER_KEY_AUTH))
        server.shutdown()

    }
}