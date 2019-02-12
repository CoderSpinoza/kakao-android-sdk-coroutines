package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.kakaotalk.entity.ChatOrder
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.kakaotalk.entity.ChatListResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class DefaultTalkApiClient(val api: TalkApi = OAuthApiFactory.kapi.create(TalkApi::class.java),
                           private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance): TalkApiClient {
    override fun sendMemo(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendMessage(receiverType: String, receiverId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun profile(secureResource: Boolean?): Single<TalkProfile> {
        return api.profile(secureResource)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun chatList(fromId: Int?,
                          limit: Int?,
                          order: ChatOrder?,
                          filter: ChatFilter?
                 ): Single<ChatListResponse> {
        return api.chatList(fromId, limit, order?.value, filter?.value)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun sendMemo(templateId: String,
                          templateArgs: Map<String, String>?): Completable {
        return api.sendMemo(templateId, templateArgs)
                .compose(apiErrorInterceptor.handleCompletableError())
    }

    override fun sendMessage(receiverType: String,
                             receiverId: String,
                             templateId: String,
                             templateArgs: Map<String, String>?): Completable {
        return api.sendMessage(receiverType, receiverId, templateId, templateArgs)
                .compose(apiErrorInterceptor.handleCompletableError())
    }
}