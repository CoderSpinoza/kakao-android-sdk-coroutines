package com.kakao.sdk.login.data

import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.network.Constants
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

//        val repo = object : AccessTokenRepo {
//            override fun toCache(accessTokenResponse: AccessTokenResponse): AccessToken {
//                return
//            }
//
//            override fun clearCache() {
//            }
//
//            override fun fromCache(): AccessToken {
//            }
//
//        }
        interceptor = AccessTokenInterceptor(AccessTokenRepo.instance)
    }

    @Test
    fun interceptor() {
        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        val server = MockWebServer()
        server.start()

        server.enqueue(MockResponse())
        client.newCall(Request.Builder().url(server.url("/")).build()).execute()
        val request = server.takeRequest()

        assertEquals(String.format("%s %s", Constants.BEARER, "access_token"), request.getHeader(Constants.AUTHORIZATION))
        server.shutdown()

    }
}