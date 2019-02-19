package com.kakao.sdk.user

import com.google.gson.JsonObject
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.user.entity.UserIdResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class UserIdResponseTest {
    @ValueSource(strings = ["user_id"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/$path.json")
        val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
        val response = KakaoGsonFactory.base.fromJson(body, UserIdResponse::class.java)
        assertEquals(expected[Constants.ID].asLong, response.id)
    }
}