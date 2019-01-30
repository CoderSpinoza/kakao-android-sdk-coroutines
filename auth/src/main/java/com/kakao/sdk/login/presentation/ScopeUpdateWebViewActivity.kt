package com.kakao.sdk.login.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.ResultReceiver
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.kakao.sdk.login.R

class ScopeUpdateWebViewActivity : Activity() {

    lateinit var webview: WebView
    lateinit var resultReeiver: ResultReceiver

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scope_update_web_view)

        val url = intent.getStringExtra(KEY_URL)
        val headers = intent.getParcelableExtra(KEY_HEADERS) as Bundle

        val headersMap = mutableMapOf<String, String>()
        for (key in headers.keySet()) {
            headersMap[key] = headers[key] as String
        }

        resultReeiver = intent.getParcelableExtra(KEY_RESULT_RECEIVER)
        webview = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = ScopeUpdateWebViewClient()

        webview.loadUrl(url, headersMap)
    }

    companion object {
        const val KEY_URL = "key.url"
        const val KEY_HEADERS = "key.extra.headers"
        const val KEY_RESULT_RECEIVER = "key.result.receiver"
    }

    inner class ScopeUpdateWebViewClient: WebViewClient() {
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (request == null) {
                return false
            }
            val uri = request.url
            val scheme = uri.scheme ?: return true
            val host = uri.host ?: return true
            if (scheme.startsWith("kakao")  && host.startsWith("oauth")) {
                val bundle = Bundle()
                bundle.putString(KEY_URL, uri.toString())
                resultReeiver.send(Activity.RESULT_OK, bundle)
                finish()
            }
            return true
        }

    }
}
