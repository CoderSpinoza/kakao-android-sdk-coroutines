package com.kakao.sdk.sample.story

import android.app.Application
import com.kakao.sdk.common.ApplicationContextInfo
import com.kakao.sdk.common.KakaoSdkProvider

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdkProvider.applicationContextInfo =
                ApplicationContextInfo(
                        context = this, clientId = "dd4e9cb75815cbdf7d87ed721a659baf",
                        clientSecret = "50LxgHsF3Q3ayNa3nJpTTMEfBR8KkY7X")
    }
}