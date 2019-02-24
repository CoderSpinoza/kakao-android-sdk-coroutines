package com.kakao.sdk.kakaolink

import android.content.Intent
import com.kakao.sdk.message.template.DefaultTemplate

/**
 * @author kevin.kang. Created on 19/02/2019..
 */
interface KakaoLinkClient {

    suspend fun isKakaoLinkAvailable(): Boolean

    suspend fun customTemplateIntent(templateId: String,
                             templateArgs: Map<String, String>? = null,
                             serverCallbackArgs: Map<String, String> = mapOf()): Intent

    suspend fun defaultTemplateIntent(defaultTemplate: DefaultTemplate,
                              serverCallbackArgs: Map<String, String>? = null): Intent

    suspend fun scrapTemplateIntent(url: String,
                            templateId: String? = null,
                            templateArgs: Map<String, String>? = null,
                            serverCallbackArgs: Map<String, String>? = null): Intent
}