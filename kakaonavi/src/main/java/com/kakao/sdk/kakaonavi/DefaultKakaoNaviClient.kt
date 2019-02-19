package com.kakao.sdk.kakaonavi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.kakao.sdk.common.ApplicationInfo
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaonavi.entity.KakaoNaviParams

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class DefaultKakaoNaviClient(private val applicationInfo: ApplicationInfo = ApplicationInfo()): KakaoNaviClient {
    override fun shareDestinationUri(context: Context, params: KakaoNaviParams): Uri =
            baseUriBuilder(context, params).path("sharePoi.html").build()

    override fun navigateUri(context: Context, params: KakaoNaviParams): Uri =
            baseUriBuilder(context, params).path("navigate.html").build()

    private fun baseUriBuilder(context: Context, params: KakaoNaviParams): Uri.Builder = Uri.Builder()
            .scheme(Constants.NAVI_WEB_SCHEME)
            .authority("kakaonavi-wguide.kakao.com")
            .appendQueryParameter(Constants.PARAM, KakaoGsonFactory.base.toJson(params))
            .appendQueryParameter(Constants.APIVER, "1.0")
            .appendQueryParameter(Constants.APPKEY, applicationInfo.clientId)
            .appendQueryParameter(Constants.EXTRAS, Utility.getExtras(context).toString())

    override fun isKakaoNaviInstalled(context: Context): Boolean {
        return context.packageManager.getLaunchIntentForPackage(Constants.NAVI_PACKAGE) != null
    }

    override fun shareDestinationIntent(context: Context, params: KakaoNaviParams): Intent {
        val uri = baseUriBuilder(context, params).scheme(Constants.NAVI_SCHEME)
                .authority("sharePoi").build()
        Log.e("uri", uri.toString())
        return Intent(Intent.ACTION_VIEW, uri)
    }

    override fun navigateIntent(context: Context, params: KakaoNaviParams): Intent {
        val uri = baseUriBuilder(context, params).scheme(Constants.NAVI_SCHEME)
                .authority("navigate").build()
        Log.e("uri", uri.toString())
        return Intent(Intent.ACTION_VIEW, uri)
    }
}