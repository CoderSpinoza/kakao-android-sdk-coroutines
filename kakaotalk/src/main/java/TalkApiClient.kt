package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.ChatListResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile
import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.kakaotalk.entity.ChatOrder

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface TalkApiClient {
    suspend fun profile(secureResource: Boolean? = null): TalkProfile

    suspend fun chatList(
        fromId: Int? = null,
        limit: Int? = null,
        order: ChatOrder? = null,
        filter: ChatFilter? = null
    ): ChatListResponse

    suspend fun sendMemo(
        templateId: String,
        templateArgs: Map<String, String>? = null
    )

    suspend fun sendMessage(
        receiverType: String,
        receiverId: String,
        templateId: String,
        templateArgs: Map<String, String>? = null
    )

    suspend fun sendMessage(
        receiverType: String,
        receiverId: String
    )

    companion object {
        val instance by lazy { DefaultTalkApiClient() as TalkApiClient }
    }
}