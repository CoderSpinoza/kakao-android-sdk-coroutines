package com.kakao.sdk.kakaotalk

import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.kakaotalk.entity.*
import com.kakao.sdk.message.template.DefaultTemplate

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class DefaultTalkApiClient(
        val api: TalkApi = OAuthApiFactory.kapi.create(TalkApi::class.java),
        private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance
) : TalkApiClient {

    override suspend fun profile(): TalkProfile {
        return apiErrorInterceptor.handleApiError { api.profile() }
    }

    override suspend fun customMemo(
            templateId: String,
            templateArgs: Map<String, String>?
    ) {
        return apiErrorInterceptor.handleApiError { api.customMemo(templateId, templateArgs) }
    }

    override suspend fun defaultMemo(templateParams: DefaultTemplate) {
        return apiErrorInterceptor.handleApiError { api.defaultMemo(templateParams) }
    }

    override suspend fun scrapMemo(requestUrl: String, templateId: Long?, templateArgs: Map<String, String>?) {
        return apiErrorInterceptor.handleApiError {
            api.scrapMemo(requestUrl, templateId, templateArgs)
        }
    }

    override suspend fun plusFriends(publicIds: String?): PlusFriendsResponse {
        return apiErrorInterceptor.handleApiError {
            api.plusFriends(publicIds)
        }
    }
}