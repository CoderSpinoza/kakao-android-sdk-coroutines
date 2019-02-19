package com.kakao.sdk.kakaonavi

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kakao.sdk.kakaonavi.entity.KakaoNaviParams

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
interface KakaoNaviClient {
    /**
     * 카카오내비 앱 설치 여부.
     *
     * @return true if installed, false otherwise.
     */
    fun isKakaoNaviInstalled(context: Context): Boolean

    fun shareDestinationIntent(params: KakaoNaviParams): Intent

    fun navigateIntent(params: KakaoNaviParams): Intent

    fun shareDestinationUri(params: KakaoNaviParams): Uri

    fun navigateUri(params: KakaoNaviParams): Uri

    companion object {
        val instance: KakaoNaviClient by lazy { DefaultKakaoNaviClient() }
    }
}