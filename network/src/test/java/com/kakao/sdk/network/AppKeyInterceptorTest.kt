package com.kakao.sdk.network

import android.app.Application
import android.os.Bundle
import com.kakao.sdk.network.data.AppKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog

import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
@RunWith(RobolectricTestRunner::class)
class AppKeyInterceptorTest {
    lateinit var interceptor: AppKeyInterceptor
    @Before
    fun setup() {
        ShadowLog.stream = System.out

        val bundle = Bundle()
        bundle.putString(Constants.META_APP_KEY, "test_app_key")
        val application = androidx.test.core.app.ApplicationProvider.getApplicationContext<Application>()
        application.applicationInfo.metaData = bundle
        ApplicationProvider.application = application
        interceptor = AppKeyInterceptor()
    }

    @Test
    fun  interceptor() {
        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        val server = MockWebServer()
        server.start()

        server.enqueue(MockResponse())
        client.newCall(Request.Builder().url(server.url("/")).build()).execute()
        val request = server.takeRequest()

        assertEquals(String.format("%s %s", Constants.KAKAO_AK, "test_app_key"), request.getHeader(Constants.AUTHORIZATION))
        server.shutdown()
    }
}