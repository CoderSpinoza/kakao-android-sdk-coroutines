package com.kakao.sdk.network

import android.os.Bundle
import com.kakao.sdk.network.data.AppKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog

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
        bundle.putString(StringSet.META_APP_KEY, "test_app_key")
        RuntimeEnvironment.application.applicationInfo.metaData = bundle
        ApplicationProvider.application = RuntimeEnvironment.application
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

        Assert.assertEquals(String.format("%s %s", StringSet.HEADER_PREFIX_APP_KEY, "test_app_key"), request.getHeader(StringSet.HEADER_KEY_AUTH))
        server.shutdown()
    }
}