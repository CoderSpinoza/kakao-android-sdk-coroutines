package com.kakao.sdk.friends.entity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.friends.data.FriendsResponse
import com.kakao.sdk.network.Utility
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class FriendsResponseTest {
    @ValueSource(strings = ["friends1.json", "friends2.json"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/friends/$path")

        val expected = Gson().fromJson(body, JsonObject::class.java)
        val response = Gson().fromJson(body, FriendsResponse::class.java)
        assertEquals(expected["total_count"].asInt, response.totalCount)
        assertEquals(expected["result_id"].asString,  response.resultId)
        assertEquals(expected["elements"].asJsonArray.size(), response.friends.size)
    }
}