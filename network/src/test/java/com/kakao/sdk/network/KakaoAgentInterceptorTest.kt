package com.kakao.sdk.network

import android.content.pm.PackageInfo
import android.content.pm.Signature
import android.os.Build
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
import org.robolectric.shadows.ShadowPackageManager

import org.robolectric.Shadows.*
import org.junit.Assert.*
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
@RunWith(RobolectricTestRunner::class)
class KakaoAgentInterceptorTest {
    lateinit var interceptor: KakaoAgentInterceptor
    lateinit var shadowPackageManger: ShadowPackageManager
    @Before
    fun setup() {
        ShadowLog.stream = System.out

        val packageManager = RuntimeEnvironment.application.packageManager
        shadowPackageManger = shadowOf(packageManager)

        val info = PackageInfo()
        info.packageName = RuntimeEnvironment.application.packageName
        info.versionName = "1.0.0"
        info.signatures = arrayOf(Signature("00000000"))
        shadowPackageManger.addPackage(info)

        ApplicationProvider.application = RuntimeEnvironment.application
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

        val headerMap = parseKAHeader(request.getHeader(StringSet.HEADER_KEY_KA))
        assertEquals(BuildConfig.VERSION_NAME, headerMap[StringSet.KA_KEY_SDK])
        assertEquals(String.format("android-%s", Build.VERSION.SDK_INT), headerMap[StringSet.KA_KEY_OS])
        assertEquals(Locale.getDefault().country.toUpperCase(), headerMap[StringSet.KA_KEY_LANG])
        assertTrue(headerMap.containsKey(StringSet.KA_KEY_ORIGIN))
        assertTrue(headerMap.containsKey(StringSet.KA_KEY_DEVICE))
        assertEquals(RuntimeEnvironment.application.packageName, headerMap[StringSet.KA_KEY_PACKAGE])
        assertEquals("1.0.0", headerMap[StringSet.KA_KEY_APP_VER])
    }

    fun parseKAHeader(header: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        header.split(" ").map { kv -> kv.split("/") }.forEach { map[it[0]] = it[1] }
        return map
    }
}