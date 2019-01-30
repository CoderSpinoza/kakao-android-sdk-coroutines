package com.kakao.sdk.user

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.login.Constants
import com.kakao.sdk.network.Utility
import com.kakao.sdk.user.data.AccessTokenInfo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class AccessTokenInfoTest {

    @ValueSource(strings = ["internal", "external"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/token_info/$path.json")
        val expected = Gson().fromJson(body, JsonObject::class.java)
        val response = Gson().fromJson(body, AccessTokenInfo::class.java)

        assertEquals(expected[Constants.APPID].asLong, response.appId)
        assertEquals(expected[Constants.EXPIRESINMILLIS].asLong, response.expiresInMillis)

        if (expected.has(Constants.KACCOUNT_ID)) {
            assertEquals(expected[Constants.KACCOUNT_ID].asLong, response.kaccountId)
        } else {
            assertNull(response.kaccountId)
        }
        assertEquals(expected[Constants.ID].asLong, response.id)
    }
}