package com.kakao.sdk.auth.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ResultReceiver
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.auth.R
import com.kakao.sdk.auth.exception.AuthCancelException
import com.kakao.sdk.auth.exception.AuthWebViewException
import com.kakao.sdk.common.KakaoSdkProvider

/**
 * 유저의 scope 업데이트는 RT 헤더를 사용하기 떄문에 Chrome Custom Tab 이 아닌 WebView 로 진행해야 함.
 *
 * @suppress
 */
class ScopeUpdateWebViewActivity : Activity() {

    internal lateinit var webView: WebView
    private lateinit var resultReceiver: ResultReceiver
    private lateinit var redirectUri: String
    private val headersMap = mutableMapOf<String, String>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scope_update_web_view)

        val uri = intent.getParcelableExtra<Uri>(Constants.KEY_URL)
        val headers = intent.getParcelableExtra(Constants.KEY_HEADERS) as Bundle

        redirectUri = intent.getStringExtra(Constants.KEY_REDIRECT_URI)
        for (key in headers.keySet()) {
            headersMap[key] = headers[key] as String
        }

        resultReceiver = intent.getParcelableExtra(Constants.KEY_RESULT_RECEIVER)
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = ScopeUpdateWebViewClient()

        webView.loadUrl(uri.toString(), headersMap)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        val bundle = Bundle()
        bundle.putSerializable(Constants.KEY_EXCEPTION, AuthCancelException())
        resultReceiver.send(Activity.RESULT_CANCELED, bundle)
        super.onBackPressed()
    }

    inner class ScopeUpdateWebViewClient : WebViewClientCompat() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url
            if (uri.toString().startsWith(redirectUri)) {
                val bundle = Bundle()
                bundle.putParcelable(Constants.KEY_URL, uri)
                resultReceiver.send(Activity.RESULT_OK, bundle)
                finish()
                return true
            }
            if (uri.authority?.endsWith(KakaoSdkProvider.serverHosts.kauth) == true ||
                    uri.authority?.endsWith(KakaoSdkProvider.serverHosts.kapi) == true ||
                    uri.authority?.endsWith(KakaoSdkProvider.serverHosts.account) == true) {
                webView.loadUrl(uri.toString(), headersMap)
                return true
            }
            try {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            return true
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceErrorCompat
        ) {
            val bundle = Bundle()
            bundle.putSerializable(
                Constants.KEY_EXCEPTION,
                AuthWebViewException(
                        error.errorCode,
                        error.description.toString(),
                        request.url.toString()))
            resultReceiver.send(Activity.RESULT_CANCELED, bundle)
            finish()
        }


        @Suppress("OverridingDeprecatedMember", "DEPRECATION")
        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            val bundle = Bundle()
            bundle.putSerializable(
                    Constants.KEY_EXCEPTION,
                    AuthWebViewException(
                            errorCode,
                            description.toString(),
                            failingUrl.toString()))
            resultReceiver.send(Activity.RESULT_CANCELED, bundle)
            finish()
        }
    }
}
