package com.kakao.sdk.kakaolink

import android.content.Intent
import android.net.Uri
import com.kakao.sdk.common.ContextInfo
import com.kakao.sdk.common.KakaoSdkProvider
import com.kakao.sdk.message.template.DefaultTemplate
import com.kakao.sdk.network.ApiFactory

/**
 * @author kevin.kang. Created on 19/02/2019..
 */
class DefaultKakaoLinkClient(val contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo,
                             val api: KakaoLinkApi = ApiFactory.kapi.create(KakaoLinkApi::class.java)
): KakaoLinkClient {
    override suspend fun isKakaoLinkAvailable(): Boolean {
        return true
    }

    override suspend fun customTemplateIntent(templateId: String, templateArgs: Map<String, String>?, serverCallbackArgs: Map<String, String>): Intent {
        val response = api.validateCustom(templateId, templateArgs).await()
        return Intent()
//                .map { response -> Intent() }
    }

    override suspend fun defaultTemplateIntent(defaultTemplate: DefaultTemplate, serverCallbackArgs: Map<String, String>?): Intent {
        val response = api.validateDefault(defaultTemplate).await()
        val builder = Uri.Builder().scheme(Constants.LINK_SCHEME).authority(Constants.LINK_AUTHORITY)
                .appendQueryParameter(Constants.LINKVER, Constants.LINKVER_40)

        builder.appendQueryParameter(Constants.TEMPLATE_ID, response.templateId)
        builder.appendQueryParameter(Constants.TEMPLATE_ARGS, response.templateArgs.toString())
        builder.appendQueryParameter(Constants.TEMPLATE_JSON, response.templateMsg.toString())
        return Intent(Intent.ACTION_SEND, builder.build())
//                .map { response ->
//                    val builder = Uri.Builder().scheme(Constants.LINK_SCHEME).authority(Constants.LINK_AUTHORITY)
//                            .appendQueryParameter(Constants.LINKVER, Constants.LINKVER_40)
//
//                    builder.appendQueryParameter(Constants.TEMPLATE_ID, response.templateId)
//                    builder.appendQueryParameter(Constants.TEMPLATE_ARGS, response.templateArgs.toString())
//                    builder.appendQueryParameter(Constants.TEMPLATE_JSON, response.templateMsg.toString())
//
//                    // add extras
//                    return@map builder.build()
//                }.map { Intent(Intent.ACTION_SEND, it) }
    }

    override suspend fun scrapTemplateIntent(url: String, templateId: String?, templateArgs: Map<String, String>?, serverCallbackArgs: Map<String, String>?): Intent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}