package com.kakao.sdk.auth.presentation

import android.webkit.WebResourceRequest

abstract class MockWebResourceRequest : WebResourceRequest {
    override fun isRedirect(): Boolean {
        return true
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