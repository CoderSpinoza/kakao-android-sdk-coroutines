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
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.net.HttpURLConnection

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
            val uri = UriUtility.updateScopeUri(clientId, redirectUri, approvalType, scopes)
            val intent = scopeUpdateIntent(context, uri, redirectUri, kaHeader, emitter)
            context.startActivity(intent)
        }
    }

    fun scopeUpdateIntent(context: Context,
                          uri: Uri,
                          redirectUri: String,
                          kaHeader: String,
                          emitter: SingleEmitter<String>): Intent {
        val headers = scopeUpdateHeaders(tokenObservable.blockingFirst().refreshToken, kaHeader)
        val resultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                this@DefaultAuthCodeService.onReceivedResult(resultCode, resultData, emitter)
            }
        }
        return UriUtility.scopeUpdateIntent(context, uri, redirectUri, headers, resultReceiver)
    }

    fun scopeUpdateHeaders(refreshToken: String?, kaHeader: String): Bundle {
        val headers = Bundle()
        if (refreshToken != null) {
            headers.putString(Constants.RT, refreshToken)
        }
        headers.putString(com.kakao.sdk.common.Constants.KA, kaHeader)
        return headers
    }

    fun onReceivedResult(resultCode: Int, resultData: Bundle?, emitter: SingleEmitter<String>) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = resultData?.getParcelable(ScopeUpdateWebViewActivity.KEY_URL) as Uri
            val code = uri.getQueryParameter(Constants.CODE)
            if (code != null) {
                emitter.onSuccess(code)
            } else {
                val error = uri.getQueryParameter(Constants.ERROR)
                val errorDescription = uri.getQueryParameter(Constants.ERROR_DESCRIPTION)
                emitter.onError(AuthResponseException(HttpURLConnection.HTTP_MOVED_TEMP, AuthErrorResponse(error, errorDescription)))
            }
        } else {
            val exception = resultData?.getSerializable(ScopeUpdateWebViewActivity.KEY_EXCEPTION) as AuthException
            emitter.onError(exception)
        }
    }
}