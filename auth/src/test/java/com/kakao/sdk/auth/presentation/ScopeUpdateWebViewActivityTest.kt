package com.kakao.sdk.auth.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.test.core.app.ApplicationProvider
import androidx.webkit.WebResourceErrorCompat
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.auth.TestUriUtility
import com.kakao.sdk.auth.exception.AuthWebViewException
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
open class ScopeUpdateWebViewActivityTest {

    lateinit var activity: ScopeUpdateWebViewActivity

    @Test fun redirectUri() {
        val uri = UriUtility.updateScopeUri("client_id", "kakao://oauth",
                "individual", listOf("scope1", "scope2")).buildUpon()
                .build()
        val receiver = Mockito.spy(object: ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                Assertions.assertEquals(Activity.RESULT_OK, resultCode)
                Assertions.assertNotNull(resultData)
                Assertions.assertEquals("kakao://oauth", resultData!![ScopeUpdateWebViewActivity.KEY_URL]!!.toString())
            }
        })

        val headers = Bundle()
        headers.putString(Constants.RT, "refresh_token")
        val intent = UriUtility.scopeUpdateIntent(ApplicationProvider.getApplicationContext(),
                uri, "kakao://oauth", headers, receiver)

        activity = Robolectric.buildActivity(ScopeUpdateWebViewActivity::class.java, intent)
                .create().resume().get()
        activity.webViewClient.shouldOverrideUrlLoading(activity.webView,
                MockWebResourceRequest(Uri.parse("kakao://oauth")))
    }

    @Test fun onBackPressed() {
        val uri = TestUriUtility.successfulRedirectUri()
        val receiver = Mockito.spy(object: ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                Assertions.assertEquals(Activity.RESULT_CANCELED, resultCode)
                Assertions.assertNotNull(resultData)
                Assertions.assertEquals(1, resultData?.size())
            }
        })

        val intent = UriUtility.scopeUpdateIntent(ApplicationProvider.getApplicationContext(),
                uri, "kakao://oauth", Bundle(), receiver)
        activity = Robolectric.buildActivity(ScopeUpdateWebViewActivity::class.java, intent)
                .create().resume().get()
        activity.onBackPressed()
    }

    @Test fun onReceivedError() {
        val uri = TestUriUtility.successfulRedirectUri()
        val receiver = Mockito.spy(object: ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                Assertions.assertEquals(Activity.RESULT_CANCELED, resultCode)
                Assertions.assertNotNull(resultData)
                Assertions.assertEquals(1, resultData?.size())

                val exception = resultData?.getSerializable(ScopeUpdateWebViewActivity.KEY_EXCEPTION)
                Assertions.assertEquals(AuthWebViewException::class.java, exception?.javaClass)
                val casted = exception as AuthWebViewException
                Assertions.assertEquals(WebViewClient.ERROR_FAILED_SSL_HANDSHAKE, casted.errorCode)
            }
        })
        val intent = UriUtility.scopeUpdateIntent(ApplicationProvider.getApplicationContext(),
                uri, "kakao://oauth", Bundle(), receiver)

        activity = Robolectric.buildActivity(ScopeUpdateWebViewActivity::class.java, intent)
                .create().resume().get()

        activity.webViewClient.onReceivedError(activity.webView,
                MockWebResourceRequest(Uri.parse("kakao://oauth")),
                object : WebResourceErrorCompat() {
                    override fun getDescription(): CharSequence {
                        return "error_description"
                    }

                    override fun getErrorCode(): Int {
                        return WebViewClient.ERROR_FAILED_SSL_HANDSHAKE
                    }
                })
    }
}