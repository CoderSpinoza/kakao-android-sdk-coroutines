package com.kakao.sdk.login.data

import com.kakao.sdk.login.accesstoken.AccessToken
import com.kakao.sdk.network.Constants
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class AccessTokenInterceptorTest {
    lateinit var interceptor: AccessTokenInterceptor

    @BeforeEach
    fun setup() {
        interceptor = AccessTokenInterceptor(Observable.just(AccessToken("access_token")))
    }

    @Test
    fun interceptor() {
        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        val server = MockWebServer()
        server.start()

        server.enqueue(MockResponse())
        client.newCall(Request.Builder().url(server.url("/")).build()).execute()
        val request = server.takeRequest()

        assertEquals("${Constants.BEARER} access_token", request.getHeader(Constants.AUTHORIZATION))
        server.shutdown()
    }
}