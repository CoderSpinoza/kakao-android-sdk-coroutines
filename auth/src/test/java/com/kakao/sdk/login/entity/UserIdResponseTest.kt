package com.kakao.sdk.login.entity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.login.Constants
import com.kakao.sdk.network.Utility
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class UserIdResponseTest {
    @ValueSource(strings = ["user_id"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/$path.json")
        val expected = Gson().fromJson(body, JsonObject::class.java)
        val response = Gson().fromJson(body, com.kakao.sdk.user.entity.UserIdResponse::class.java)
        assertEquals(expected[Constants.ID].asLong, response.id)
    }
}