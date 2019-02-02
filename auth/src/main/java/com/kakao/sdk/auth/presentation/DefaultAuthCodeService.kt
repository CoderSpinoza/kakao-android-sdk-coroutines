package com.kakao.sdk.auth.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.auth.data.AccessToken
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class DefaultAuthCodeService(private val tokenObservable: Observable<AccessToken>) : AuthCodeService {
    override fun requestAuthCode(context: Context,
                                 clientId: String,
                                 redirectUri: String,
                                 approvalType: String,
                                 kaHeader: String) {
        context.startActivity(Intent(context, AuthCodeCustomTabsActivity::class.java))
    }

    override fun requestAuthCode(context: Context,
                                 scopes: List<String>,
                                 clientId: String,
                                 redirectUri: String,
                                 approvalType: String,
                                 kaHeader: String
    ): Single<String> {
        return Single.create<String> { emitter ->
            val uri = updateScopeUri(clientId, redirectUri, approvalType, scopes)

            val headers = Bundle()
            headers.putString(Constants.RT, tokenObservable.blockingFirst().refreshToken)
            headers.putString(com.kakao.sdk.network.Constants.KA, kaHeader)

            val intent = Intent(context, ScopeUpdateWebViewActivity::class.java)
            intent.putExtra(ScopeUpdateWebViewActivity.KEY_URL, uri)
            intent.putExtra(ScopeUpdateWebViewActivity.KEY_HEADERS, headers)

            val resultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    this@DefaultAuthCodeService.onReceivedResult(emitter, resultCode, resultData)
                }
            }

            intent.putExtra(ScopeUpdateWebViewActivity.KEY_RESULT_RECEIVER, resultReceiver)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

            context.startActivity(intent)
        }
    }

    fun onReceivedResult(emitter: SingleEmitter<String>, resultCode: Int, resultData: Bundle?) {
        if (resultCode == Activity.RESULT_OK) {
            val redirectUri = resultData?.getString(ScopeUpdateWebViewActivity.KEY_URL)
            val uri = Uri.parse(redirectUri)
            val code = uri.getQueryParameter("code")
            if (code != null) {
                emitter.onSuccess(code)
            } else {
                emitter.onError(Throwable("Error"))
            }
        } else {
            emitter.onError(Throwable("hihi"))
        }
    }

    fun updateScopeUri(clientId: String, redirectUri: String, approvalType: String, scopes: List<String>): String {
        val builder = Uri.Builder().scheme(com.kakao.sdk.network.Constants.SCHEME).authority(com.kakao.sdk.network.Constants.KAUTH).path(Constants.AUTHORIZE_PATH)
                .appendQueryParameter(Constants.CLIENT_ID, clientId)
                .appendQueryParameter(Constants.REDIRECT_URI, redirectUri)
                .appendQueryParameter(Constants.RESPONSE_TYPE, Constants.CODE)
                .appendQueryParameter(Constants.APPROVAL_TYPE, approvalType)
                .appendQueryParameter(Constants.SCOPE, scopes.joinToString(","))
        return builder.build().toString()
    }
}