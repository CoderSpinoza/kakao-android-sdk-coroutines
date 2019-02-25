package com.kakao.sdk.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import com.kakao.sdk.auth.exception.AuthException
import com.kakao.sdk.auth.exception.AuthResponseException
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AuthErrorResponse
import com.kakao.sdk.auth.presentation.AuthCodeCustomTabsActivity
import com.kakao.sdk.auth.presentation.ScopeUpdateWebViewActivity
import com.kakao.sdk.auth.presentation.UriUtility
import com.kakao.sdk.common.Constants as CommonConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 20..
 */
@ExperimentalCoroutinesApi
class DefaultAuthCodeService(private val tokenChannel: BroadcastChannel<AccessToken>)
    : AuthCodeService {
    override fun requestAuthCode(
        context: Context,
        clientId: String,
        redirectUri: String,
        approvalType: String,
        kaHeader: String
    ) {
        context.startActivity(Intent(context, AuthCodeCustomTabsActivity::class.java))
    }

    override suspend fun requestAuthCode(
        context: Context,
        scopes: List<String>,
        clientId: String,
        redirectUri: String,
        approvalType: String,
        kaHeader: String
    ): String = suspendCoroutine { cont ->
        val uri = UriUtility.updateScopeUri(clientId, redirectUri, approvalType, scopes)
        val intent = scopeUpdateIntent(context, uri, redirectUri, kaHeader, cont)
        context.startActivity(intent)
    }

    fun scopeUpdateIntent(
        context: Context,
        uri: Uri,
        redirectUri: String,
        kaHeader: String,
        continuation: Continuation<String>
    ): Intent {
        return runBlocking {
            val headers = scopeUpdateHeaders(
                    tokenChannel.openSubscription().receive().refreshToken,
                    kaHeader)
            val resultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    this@DefaultAuthCodeService
                            .onReceivedResult(resultCode, resultData, continuation)
                }
            }
            UriUtility
                    .scopeUpdateIntent(context, uri, redirectUri, headers, resultReceiver)
        }
    }

    fun scopeUpdateHeaders(refreshToken: String?, kaHeader: String): Bundle {
        val headers = Bundle()
        if (refreshToken != null) {
            headers.putString(Constants.RT, refreshToken)
        }
        headers.putString(CommonConstants.KA, kaHeader)
        return headers
    }

    fun onReceivedResult(resultCode: Int, resultData: Bundle?, continuation: Continuation<String>) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = resultData?.getParcelable(ScopeUpdateWebViewActivity.KEY_URL) as Uri
            val code = uri.getQueryParameter(Constants.CODE)
            if (code != null) {
                continuation.resume(code)
            } else {
                val error = uri.getQueryParameter(Constants.ERROR)
                val errorDescription = uri.getQueryParameter(Constants.ERROR_DESCRIPTION)
                continuation.resumeWithException(
                        AuthResponseException(
                                HttpURLConnection.HTTP_MOVED_TEMP,
                                AuthErrorResponse(error, errorDescription)
                        )
                )
            }
        } else {
            val exception =
                    resultData?.getSerializable(ScopeUpdateWebViewActivity.KEY_EXCEPTION)
                            as AuthException
            continuation.resumeWithException(exception)
        }
    }
}