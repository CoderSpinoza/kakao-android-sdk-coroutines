package com.kakao.sdk.kakaonavi

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kakao.sdk.common.*
import com.kakao.sdk.kakaonavi.entity.KakaoNaviParams

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class DefaultKakaoNaviClient(private val applicationInfo: ApplicationInfo = KakaoSdkProvider.applicationContextInfo,
                             private val contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo): KakaoNaviClient {
    override fun shareDestinationUri(params: KakaoNaviParams): Uri =
            baseUriBuilder(params).path("${Constants.SHARE_POI}.html").build()

    override fun navigateUri(params: KakaoNaviParams): Uri =
            baseUriBuilder(params).path("${Constants.NAVIGATE}.html").build()

    private fun baseUriBuilder(params: KakaoNaviParams): Uri.Builder = Uri.Builder()
            .scheme(Constants.NAVI_WEB_SCHEME)
            .authority(Constants.NAVI_WEB_HOST)
            .appendQueryParameter(Constants.PARAM, KakaoGsonFactory.base.toJson(params))
            .appendQueryParameter(Constants.APIVER, Constants.APIVER_10)
            .appendQueryParameter(Constants.APPKEY, applicationInfo.clientId)
            .appendQueryParameter(com.kakao.sdk.common.Constants.EXTRAS, contextInfo.extras.toString())

    override fun isKakaoNaviInstalled(context: Context): Boolean {
        return context.packageManager.getLaunchIntentForPackage(Constants.NAVI_PACKAGE) != null
    }

    override fun shareDestinationIntent(params: KakaoNaviParams): Intent {
        val uri = baseUriBuilder(params).scheme(Constants.NAVI_SCHEME)
                .authority(Constants.SHARE_POI).build()
        return Intent(Intent.ACTION_VIEW, uri)
    }

    override fun navigateIntent(params: KakaoNaviParams): Intent {
        val uri = baseUriBuilder(params).scheme(Constants.NAVI_SCHEME)
                .authority(Constants.NAVIGATE).build()
        return Intent(Intent.ACTION_VIEW, uri)
    }
}