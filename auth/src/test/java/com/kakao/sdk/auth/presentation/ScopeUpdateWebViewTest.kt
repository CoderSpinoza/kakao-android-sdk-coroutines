package com.kakao.sdk.auth.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.*
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
open class ScopeUpdateWebViewTest {
    lateinit var activity: ScopeUpdateWebViewActivity
    @Test fun setScopeUpdateWebViewClient() {
        val uri = UriUtility.updateScopeUri("client_id", "kakao://oauth",
                "individual", listOf("scope1", "scope2")).buildUpon()
                .build().toString()
        val receiver = Mockito.spy(object: ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                Assertions.assertEquals(Activity.RESULT_OK, resultCode)
                Assertions.assertNotNull(resultData)
                Assertions.assertEquals("kakao://oauth", resultData!![ScopeUpdateWebViewActivity.KEY_URL])
            }
        })

        val intent = Intent().putExtra(ScopeUpdateWebViewActivity.KEY_URL, uri)
                .putExtra(ScopeUpdateWebViewActivity.KEY_HEADERS, Bundle())
                .putExtra(ScopeUpdateWebViewActivity.KEY_RESULT_RECEIVER, receiver)
        activity = Robolectric.buildActivity(ScopeUpdateWebViewActivity::class.java, intent)
                .create().resume().get()
        activity.webViewClient.shouldOverrideUrlLoading(activity.webView, object: MockWebResourceRequest() {
            override fun getUrl(): Uri {
                return Uri.parse("kakao://oauth")
            }
        })
        Mockito.verify(receiver).send(ArgumentMatchers.eq(Activity.RESULT_OK), ArgumentMatchers.any(Bundle::class.java))
    }
}