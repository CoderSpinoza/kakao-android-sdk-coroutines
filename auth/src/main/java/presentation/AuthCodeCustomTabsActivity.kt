package com.kakao.sdk.auth.presentation

import android.app.Activity
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.CustomTabsCommonClient
import com.kakao.sdk.common.KakaoSdkProvider
import kotlin.IllegalArgumentException

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 24..
 */
class AuthCodeCustomTabsActivity : Activity() {
    private lateinit var resultReceiver: ResultReceiver
    private var customTabsConnection: ServiceConnection? = null
    private var customTabsOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras ?: throw IllegalArgumentException()
        resultReceiver = extras.getParcelable(Constants.KEY_RESULT_RECEIVER) as ResultReceiver
    }

    override fun onResume() {
        super.onResume()
        if (!customTabsOpened) {
            openChromeCustomTab()
            customTabsOpened = true
        } else {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.dataString
        val bundle = Bundle()
        if (uri == null) {
            resultReceiver.send(Activity.RESULT_CANCELED, bundle)
            finish()
            return
        }
        bundle.putParcelable(Constants.KEY_URL, intent.data)
        resultReceiver.send(Activity.RESULT_OK, bundle)
        finish()
    }

    fun openChromeCustomTab() {
        val continueUri = Uri.Builder()
                .scheme(com.kakao.sdk.network.Constants.SCHEME)
                .authority(KakaoSdkProvider.serverHosts.kauth)
                .path(Constants.AUTHORIZE_PATH)
                .appendQueryParameter(Constants.CLIENT_ID,
                        KakaoSdkProvider.applicationContextInfo.clientId)
                .appendQueryParameter(Constants.REDIRECT_URI,
                        String.format("kakao%s://oauth",
                                KakaoSdkProvider.applicationContextInfo.clientId))
                .appendQueryParameter(Constants.RESPONSE_TYPE, Constants.CODE)
                .appendQueryParameter(Constants.APPROVAL_TYPE, "individual")
                .build()
        customTabsConnection = CustomTabsCommonClient.openWithDefault(this, continueUri)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (customTabsConnection != null) {
            unbindService(customTabsConnection)
        }

    }
}