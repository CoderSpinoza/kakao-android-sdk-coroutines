package com.kakao.sdk.login.entity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.login.Constants
import com.kakao.sdk.login.data.AccessTokenResponse
import com.kakao.sdk.network.Utility
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class AccessTokenResponseTest {
    @ValueSource(strings = ["has_rt", "has_rt_and_scopes", "no_rt"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/token/$path.json")
        val expected = Gson().fromJson(body, JsonObject::class.java)
        val response = Gson().fromJson(body, AccessTokenResponse::class.java)

        if (expected.has(Constants.ACCESS_TOKEN)) {
            assertEquals(expected[Constants.ACCESS_TOKEN].asString, response.accessToken)
        }
        if (expected.has(Constants.EXPIRES_IN)) {
            assertEquals(expected[Constants.EXPIRES_IN].asLong, response.accessTokenExpiresIn)
        }
        if (expected.has(Constants.REFRESH_TOKEN)) {
            assertEquals(expected[Constants.REFRESH_TOKEN].asString, response.refreshToken)
        } else {
            assertNull(response.refreshToken)
        }
        if (expected.has(Constants.REFRESH_TOKEN_EXPIRES_IN)) {
            assertEquals(expected[Constants.REFRESH_TOKEN_EXPIRES_IN].asLong, response.refreshTokenExpiresIn)
        } else {
            assertNull(response.refreshTokenExpiresIn)
        }
        if (expected.has(Constants.TOKEN_TYPE)) {
            assertEquals(expected[Constants.TOKEN_TYPE].asString, response.tokenType)
        }
        if (expected.has(Constants.SCOPE)) {
            assertEquals(expected[Constants.SCOPE].asString, response.scopes)
        }
    }
}