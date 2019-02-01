package com.kakao.sdk.user

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.network.Utility
import com.kakao.sdk.user.data.UserIdResponse
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
        val response = Gson().fromJson(body, UserIdResponse::class.java)
        assertEquals(expected[Constants.ID].asLong, response.id)
    }
}