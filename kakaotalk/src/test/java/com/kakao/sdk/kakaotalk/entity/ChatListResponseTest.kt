package com.kakao.sdk.kakaotalk.entity

import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaotalk.Constants
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author kevin.kang. Created on 2018. 4. 29..
 */
class ChatListResponseTest {
    @ValueSource(strings = ["chat_list/list"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/$path.json")
        val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
        val response = KakaoGsonFactory.base.fromJson(body, ChatListResponse::class.java)

        assertEquals(expected[Constants.TOTAL_COUNT].asInt, response.totalCount)

        if (!expected.has(Constants.BEFORE_URL) || expected[Constants.BEFORE_URL] is JsonNull) {
            assertNull(response.beforeUrl)
        } else {
            assertEquals(expected[Constants.BEFORE_URL].asString, response.beforeUrl)
        }
        if (!expected.has(Constants.AFTER_URL) || expected[Constants.AFTER_URL] is JsonNull) {
            assertNull(response.afterUrl)
        } else {
            assertEquals(expected[Constants.AFTER_URL].asString, response.afterUrl)
        }

        assertEquals(expected[Constants.ELEMENTS].asJsonArray.size(), response.chatList.size)

        val array = expected[Constants.ELEMENTS].asJsonArray
        array.forEachIndexed { i, chatJson ->
            val expectedChat = chatJson.asJsonObject
            val chat = response.chatList[i]

            assertEquals(expectedChat[Constants.ID].asLong, chat.id)
            assertEquals(expectedChat[Constants.TITLE].asString, chat.title)
            if (Utility.hasAndNotNull(expectedChat, Constants.IMAGE_URL)) {
                assertEquals(expectedChat[Constants.IMAGE_URL].asString, chat.imageUrl)
            } else {
                assertNull(chat.imageUrl)
            }
            assertEquals(expectedChat[Constants.MEMBER_COUNT].asInt, chat.memberCount)
            assertEquals(expectedChat[Constants.CHAT_TYPE].asString, chat.chatType)
        }
    }
}