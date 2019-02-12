package com.kakao.sdk.auth.presentation

import android.net.Uri
import com.kakao.sdk.auth.Constants

object UriUtility {
    fun updateScopeUri(clientId: String, redirectUri: String, approvalType: String, scopes: List<String>): Uri {
        val builder = Uri.Builder()
                .scheme(com.kakao.sdk.network.Constants.SCHEME)
                .authority(com.kakao.sdk.network.Constants.KAUTH).path(Constants.AUTHORIZE_PATH)
                .appendQueryParameter(Constants.CLIENT_ID, clientId)
                .appendQueryParameter(Constants.REDIRECT_URI, redirectUri)
                .appendQueryParameter(Constants.RESPONSE_TYPE, Constants.CODE)
                .appendQueryParameter(Constants.APPROVAL_TYPE, approvalType)
                .appendQueryParameter(Constants.SCOPE, scopes.joinToString(","))
        return builder.build()
    }
}