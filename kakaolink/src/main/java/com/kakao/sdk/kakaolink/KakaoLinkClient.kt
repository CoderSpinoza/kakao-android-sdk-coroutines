package com.kakao.sdk.kakaolink

import android.content.Intent
import com.kakao.sdk.message.template.DefaultTemplate
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 19/02/2019..
 */
interface KakaoLinkClient {

    fun isKakaoLinkAvailable(): Boolean

    fun customTemplateIntent(templateId: String,
                             templateArgs: Map<String, String>? = null,
                             serverCallbackArgs: Map<String, String> = mapOf()): Single<Intent>

    fun defaultTemplateIntent(defaultTemplate: DefaultTemplate,
                              serverCallbackArgs: Map<String, String>? = null): Single<Intent>

    fun scrapTemplateIntent(url: String,
                            templateId: String? = null,
                            templateArgs: Map<String, String>? = null,
                            serverCallbackArgs: Map<String, String>? = null): Single<Intent>
}