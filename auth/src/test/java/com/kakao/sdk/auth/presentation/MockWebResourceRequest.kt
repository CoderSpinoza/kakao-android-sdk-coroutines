package com.kakao.sdk.auth.presentation

import android.net.Uri
import android.webkit.WebResourceRequest

class MockWebResourceRequest(private val uri: Uri) : WebResourceRequest {
    override fun isRedirect(): Boolean {
        return true
    }

    override fun getUrl(): Uri {
        return uri
    }

    override fun getRequestHeaders(): MutableMap<String, String> {
        return mutableMapOf()
    }

    override fun getMethod(): String {
        return "GET"
    }

    override fun hasGesture(): Boolean {
        return false
    }

    override fun isForMainFrame(): Boolean {
        return true
    }
}