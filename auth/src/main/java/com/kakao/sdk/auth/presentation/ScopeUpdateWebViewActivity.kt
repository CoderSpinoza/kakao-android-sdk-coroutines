package com.kakao.sdk.auth.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.ResultReceiver
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.webkit.WebViewClientCompat
import com.kakao.sdk.auth.R

/**
 * @suppress
 */
class ScopeUpdateWebViewActivity : Activity() {

    internal lateinit var webView: WebView
    internal lateinit var webViewClient: WebViewClientCompat
    private lateinit var resultReceiver: ResultReceiver

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

        resultReceiver = intent.getParcelableExtra(KEY_RESULT_RECEIVER)
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webViewClient = ScopeUpdateWebViewClient()
        webView.webViewClient = webViewClient
        webView.loadUrl(url, headersMap)
    }

    companion object {
        const val KEY_URL = "key.url"
        const val KEY_HEADERS = "key.extra.headers"
        const val KEY_RESULT_RECEIVER = "key.result.receiver"
    }

    inner class ScopeUpdateWebViewClient: WebViewClientCompat() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url
            val scheme = uri.scheme ?: return true
            val host = uri.host ?: return true
            if (scheme.startsWith("kakao")  && host.startsWith("oauth")) {
                val bundle = Bundle()
                bundle.putString(KEY_URL, uri.toString())
                resultReceiver.send(Activity.RESULT_OK, bundle)
                finish()
                return true
            }
            return false
        }
    }
}
