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
import com.kakao.sdk.auth.presentation.TalkAuthCodeActivity
import com.kakao.sdk.auth.presentation.UriUtility
import com.kakao.sdk.common.IntentResolveClient
import com.kakao.sdk.common.exception.TalkNotInstalledException
import com.kakao.sdk.common.Constants as CommonConstants
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class DefaultAuthCodeService(
        @Suppress("EXPERIMENTAL_API_USAGE") private val tokenChannel: ConflatedBroadcastChannel<AccessToken>,
        private val intentResolveClient: IntentResolveClient = IntentResolveClient()
) : AuthCodeService {
    override suspend fun requestAuthCode(
            context: Context,
            clientId: String,
            redirectUri: String,
            approvalType: String,
            kaHeader: String
    ): String = suspendCoroutine {
        context.startActivity(Intent(context, AuthCodeCustomTabsActivity::class.java)
                .putExtra(Constants.KEY_RESULT_RECEIVER, resultReceiver(it)))
    }

    fun resultReceiver(continuation: Continuation<String>): ResultReceiver {
        return object : ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                this@DefaultAuthCodeService
                        .onReceivedResult(resultCode, resultData, continuation)
            }
        }
    }

    override suspend fun requestAuthCode(
            context: Context,
            requestCode: Int,
            clientId: String,
            redirectUri: String,
            approvalType: String,
            kaHeader: String
    ): String = suspendCoroutine {

        val talkIntent = talkLoginIntent(clientId, approvalType, redirectUri, kaHeader)
        val resolvedIntent = intentResolveClient.resolveTalkIntent(context, talkIntent, 178)
        if (resolvedIntent == null) {
            it.resumeWithException(TalkNotInstalledException())
            return@suspendCoroutine
        }
        context.startActivity(
                Intent(context, TalkAuthCodeActivity::class.java)
                        .putExtra(Constants.KEY_LOGIN_INTENT, intentResolveClient.resolveTalkIntent(context, talkIntent, 178))
                        .putExtra(Constants.KEY_REQUEST_CODE, requestCode)
                        .putExtra(Constants.KEY_RESULT_RECEIVER, resultReceiver(it))
        )
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
        @Suppress("EXPERIMENTAL_API_USAGE") val headers = scopeUpdateHeaders(tokenChannel.value.refreshToken, kaHeader)
        return UriUtility
                .scopeUpdateIntent(context, uri, redirectUri, headers, resultReceiver(continuation))
    }

    fun talkLoginIntent(clientId: String, approvalType: String, redirectUri: String, kaHeader: String): Intent {
        return Intent().setAction("com.kakao.talk.intent.action.CAPRI_LOGGED_IN_ACTIVITY")
                .addCategory(Intent.CATEGORY_DEFAULT)
                .putExtra(Constants.EXTRA_APPLICATION_KEY, clientId)
                .putExtra(Constants.EXTRA_REDIRECT_URI, redirectUri)
                .putExtra(Constants.EXTRA_KA_HEADER, kaHeader)
                .putExtra(Constants.APPROVAL_TYPE, approvalType)
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
            val uri = resultData?.getParcelable(Constants.KEY_URL) as Uri
            val code = uri.getQueryParameter(Constants.CODE)
            if (code != null) {
                continuation.resume(code)
                return
            }
            val error = uri.getQueryParameter(Constants.ERROR)
            val errorDescription = uri.getQueryParameter(Constants.ERROR_DESCRIPTION)
            continuation.resumeWithException(
                    AuthResponseException(
                            HttpURLConnection.HTTP_MOVED_TEMP,
                            AuthErrorResponse(error, errorDescription)
                    )
            )
            return
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            val exception =
                    resultData?.getSerializable(Constants.KEY_EXCEPTION)
                            as AuthException
            continuation.resumeWithException(exception)
            return
        }
        throw IllegalArgumentException("Unknown resultCode in DefaultAuthCodeService#onReceivedResult()")
    }
}