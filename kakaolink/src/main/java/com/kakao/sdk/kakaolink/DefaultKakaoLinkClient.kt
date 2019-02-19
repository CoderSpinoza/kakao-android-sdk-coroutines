package com.kakao.sdk.kakaolink

import android.content.Intent
import android.net.Uri
import com.kakao.sdk.common.ContextInfo
import com.kakao.sdk.common.KakaoSdkProvider
import com.kakao.sdk.message.template.DefaultTemplate
import com.kakao.sdk.network.data.ApiSingleton
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 19/02/2019..
 */
class DefaultKakaoLinkClient(val contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo,
                             val api: KakaoLinkApi = ApiSingleton.instance.create(KakaoLinkApi::class.java)
): KakaoLinkClient {
    override fun isKakaoLinkAvailable(): Boolean {
        return true
    }

    override fun customTemplateIntent(templateId: String, templateArgs: Map<String, String>?, serverCallbackArgs: Map<String, String>): Single<Intent> {
        return api.validateCustom(templateId, templateArgs)
                .map { response -> Intent() }
    }

    override fun defaultTemplateIntent(defaultTemplate: DefaultTemplate, serverCallbackArgs: Map<String, String>?): Single<Intent> {
        return api.validateDefault(defaultTemplate)
                .map { response ->
                    val builder = Uri.Builder().scheme(Constants.LINK_SCHEME).authority(Constants.LINK_AUTHORITY)
                            .appendQueryParameter(Constants.LINKVER, Constants.LINKVER_40)

                    builder.appendQueryParameter(Constants.TEMPLATE_ID, response.templateId)
                    builder.appendQueryParameter(Constants.TEMPLATE_ARGS, response.templateArgs.toString())
                    builder.appendQueryParameter(Constants.TEMPLATE_JSON, response.templateMsg.toString())

                    // add extras
                    return@map builder.build()
                }.map { Intent(Intent.ACTION_SEND, it) }
    }

    override fun scrapTemplateIntent(url: String, templateId: String?, templateArgs: Map<String, String>?, serverCallbackArgs: Map<String, String>?): Single<Intent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}