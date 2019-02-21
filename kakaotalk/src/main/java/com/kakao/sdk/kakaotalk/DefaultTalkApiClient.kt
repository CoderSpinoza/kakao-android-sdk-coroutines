package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.kakaotalk.entity.ChatOrder
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.kakaotalk.entity.ChatListResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class DefaultTalkApiClient(val api: TalkApi = OAuthApiFactory.kapi.create(TalkApi::class.java),
                           private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance): TalkApiClient {

    override suspend fun sendMessage(receiverType: String, receiverId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun profile(secureResource: Boolean?): TalkProfile {
        return api.profile(secureResource).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun chatList(fromId: Int?,
                                  limit: Int?,
                                  order: ChatOrder?,
                                  filter: ChatFilter?
                 ): ChatListResponse {
        return api.chatList(fromId, limit, order?.value, filter?.value).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun sendMemo(templateId: String,
                                  templateArgs: Map<String, String>?) {
        return api.sendMemo(templateId, templateArgs).await()
//                .compose(apiErrorInterceptor.handleCompletableError())
    }

    override suspend fun sendMessage(receiverType: String,
                                     receiverId: String,
                                     templateId: String,
                                     templateArgs: Map<String, String>?) {
        return api.sendMessage(receiverType, receiverId, templateId, templateArgs).await()
//                .compose(apiErrorInterceptor.handleCompletableError())
    }
}