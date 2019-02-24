package com.kakao.sdk.friends.entity

import com.google.gson.JsonObject
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class FriendsResponseTest {
    @ValueSource(strings = ["friends1.json", "friends2.json"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/friends/$path")

        val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
        val response = KakaoGsonFactory.base.fromJson(body, FriendsResponse::class.java)
        assertEquals(expected["total_count"].asInt, response.totalCount)
        assertEquals(expected["result_id"].asString, response.resultId)
        assertEquals(expected["elements"].asJsonArray.size(), response.friends.size)
    }
}