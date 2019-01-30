package com.kakao.sdk.kakaotalk.data

import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.kakaotalk.entity.ChatOrder
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface TalkApiClient {
    fun profile(secureResource: Boolean? = null): Single<TalkProfile>

    fun chatList(fromId: Int? = null,
                 limit: Int? = null,
                 order: ChatOrder? = null,
                 filter: ChatFilter? = null
    ): Single<ChatListResponse>

    fun sendMemo(templateId: String,
                 templateArgs: Map<String, String>? = null): Completable

    fun sendMemo(): Completable

    fun sendMessage(receiverType: String,
                    receiverId: String,
                    templateId: String,
                    templateArgs: Map<String, String>? = null): Completable

    fun sendMessage(receiverType: String,
                    receiverId: String): Completable

    companion object {
        val instance by lazy { DefaultTalkApiClient() as TalkApiClient }
    }
}