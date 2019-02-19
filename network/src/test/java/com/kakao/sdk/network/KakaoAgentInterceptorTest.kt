package com.kakao.sdk.network

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.Signature
import android.content.pm.SigningInfo
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.kakao.sdk.common.Constants
import com.kakao.sdk.network.data.KakaoAgentInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import org.robolectric.shadows.ShadowPackageManager

import org.robolectric.Shadows.*
import org.junit.jupiter.api.Assertions.*
import org.robolectric.shadows.ShadowPackageParser
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
@RunWith(RobolectricTestRunner::class)
class KakaoAgentInterceptorTest {

    lateinit var interceptor: KakaoAgentInterceptor
    lateinit var shadowPackageManger: ShadowPackageManager
    lateinit var application: Application
    @Suppress("DEPRECATION") // Robolectric 이 API level 28 을 지원하면 ShadowSigningInfo 로 변경
    @Before
    fun setup() {
        ShadowLog.stream = System.out
        application = ApplicationProvider.getApplicationContext()
        val packageManager = application.packageManager
        shadowPackageManger = shadowOf(packageManager)

        val info = PackageInfo()
        info.packageName = application.packageName
        info.versionName = "1.0.0"
        info.signatures = arrayOf(Signature("00000000"))
        shadowPackageManger.addPackage(info)

        com.kakao.sdk.common.ApplicationProvider.application = application
        interceptor = KakaoAgentInterceptor()
    }

    @Test
    fun interceptor() {
        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        val server = MockWebServer()
        server.start()

        server.enqueue(MockResponse())
        client.newCall(Request.Builder().url(server.url("/")).build()).execute()
        val request = server.takeRequest()
        server.shutdown()

        val headerMap = parseKAHeader(request.getHeader(Constants.KA))
        assertEquals(BuildConfig.VERSION_NAME, headerMap[Constants.SDK])
        assertEquals(String.format("android-%s", Build.VERSION.SDK_INT), headerMap[Constants.OS])
        assertEquals(String.format("%s-%s", Locale.getDefault().language.toLowerCase(), Locale.getDefault().country.toUpperCase()), headerMap[Constants.LANG])
        assertTrue(headerMap.containsKey(Constants.ORIGIN))
        assertTrue(headerMap.containsKey(Constants.DEVICE))
        assertEquals(application.packageName, headerMap[Constants.ANDROID_PKG])
        assertEquals("1.0.0", headerMap[Constants.APP_VER])
    }

    fun parseKAHeader(header: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        header.split(" ").map { kv -> kv.split("/") }.forEach { map[it[0]] = it[1] }
        return map
    }
}