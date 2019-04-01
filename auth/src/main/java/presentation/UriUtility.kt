package com.kakao.sdk.auth.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.KakaoSdkProvider

object UriUtility {
    fun updateScopeUri(
        clientId: String,
        redirectUri: String,
        approvalType: String,
        scopes: List<String>
    ): Uri {
        val builder = Uri.Builder()
                .scheme(com.kakao.sdk.network.Constants.SCHEME)
                .authority(KakaoSdkProvider.serverHosts.kauth).path(Constants.AUTHORIZE_PATH)
                .appendQueryParameter(Constants.CLIENT_ID, clientId)
                .appendQueryParameter(Constants.REDIRECT_URI, redirectUri)
                .appendQueryParameter(Constants.RESPONSE_TYPE, Constants.CODE)
                .appendQueryParameter(Constants.APPROVAL_TYPE, approvalType)
                .appendQueryParameter(Constants.SCOPE, scopes.joinToString(","))
        return builder.build()
    }

    fun scopeUpdateIntent(
        context: Context,
        uri: Uri,
        redirectUri: String,
        headers: Bundle,
        resultReceiver: ResultReceiver
    ): Intent {
        return Intent(context, ScopeUpdateWebViewActivity::class.java)
                .putExtra(Constants.KEY_URL, uri)
                .putExtra(Constants.KEY_REDIRECT_URI, redirectUri)
                .putExtra(Constants.KEY_HEADERS, headers)
                .putExtra(Constants.KEY_RESULT_RECEIVER, resultReceiver)
                .addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                                or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
}