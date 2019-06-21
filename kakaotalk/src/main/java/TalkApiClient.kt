package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.*
import com.kakao.sdk.message.template.DefaultTemplate

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface TalkApiClient {
    suspend fun profile(): TalkProfile

    suspend fun customMemo(
            templateId: String,
            templateArgs: Map<String, String>? = null
    )

    suspend fun defaultMemo(templateParams: DefaultTemplate)

    suspend fun scrapMemo(
            requestUrl: String,
            templateId: Long? = null,
            templateArgs: Map<String, String>? = null)

    suspend fun plusFriends(publicIds: String? = null): PlusFriendsResponse

    suspend fun friends(offset: Int? = null, limit: Int? = null, order: String? = null): FriendsResponse

    companion object {
        val instance: TalkApiClient by lazy { DefaultTalkApiClient() }
    }
}