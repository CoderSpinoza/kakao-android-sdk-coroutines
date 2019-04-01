package com.kakao.sdk.auth.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.os.SystemClock
import android.util.Log
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.auth.R
import com.kakao.sdk.auth.exception.AuthCancelException
import com.kakao.sdk.auth.exception.AuthException
import java.lang.IllegalArgumentException
import com.kakao.sdk.auth.exception.TalkAuthCodeException


class TalkAuthCodeActivity : AppCompatActivity() {

    private lateinit var resultReceiver: ResultReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_auth_code)

        val extras = intent.extras ?: throw IllegalArgumentException()
        val requestCode = extras.getInt(Constants.KEY_REQUEST_CODE)
        val loginIntent = extras.getParcelable(Constants.KEY_LOGIN_INTENT) as Intent
        resultReceiver = extras.getParcelable(Constants.KEY_RESULT_RECEIVER) as ResultReceiver
        startActivityForResult(loginIntent, requestCode)
    }

    private fun sendError(exception: AuthException) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.KEY_EXCEPTION, exception)
        resultReceiver.send(Activity.RESULT_CANCELED, bundle)
        finish()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val bundle = Bundle()
        if (data == null || resultCode == Activity.RESULT_CANCELED) {
            sendError(AuthCancelException())
            return
        }
        if (resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            if (extras == null) {
                // no result returned from kakaotalk
                sendError(AuthException("NO result from KakaoTalk."))
                return
            }
            val errorType = extras.getString(EXTRA_ERROR_TYPE)
            val errorDescription = extras.getString(EXTRA_ERROR_DESCRIPTION)
            if (errorType == "access_denied") {
                sendError(AuthCancelException())
                return
            }
            if (errorType != null) {
                sendError(TalkAuthCodeException(errorType, errorDescription))
                return
            }
            bundle.putParcelable(Constants.KEY_URL, Uri.parse(extras[Constants.EXTRA_REDIRECT_URL] as String))
            resultReceiver.send(Activity.RESULT_OK, bundle)
            finish()
            overridePendingTransition(0, 0)
        }
        throw IllegalArgumentException("")
    }

    val EXTRA_ERROR_TYPE = "com.kakao.sdk.talk.error.type"
    val EXTRA_ERROR_DESCRIPTION = "com.kakao.sdk.talk.error.description"

    val NOT_SUPPORT_ERROR = "NotSupportError" // KakaoTalk installed but not signed up
    val UNKNOWN_ERROR = "UnknownError" // No redirect url
    val PROTOCOL_ERROR = "ProtocolError" // Wrong parameters provided
    val APPLICATION_ERROR = "ApplicationError" // Empty redirect url
    val AUTH_CODE_ERROR = "AuthCodeError"
    val CLIENT_INFO_ERROR = "ClientInfoError" // Could not fetch app info

}
