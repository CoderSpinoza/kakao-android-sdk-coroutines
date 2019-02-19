package com.kakao.sdk.ageauth

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.common.Utility
import com.kakao.sdk.ageauth.entity.AgeAuthResponse
import com.kakao.sdk.common.KakaoGsonFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class AgeAuthResponseTest {
    @ValueSource(strings = ["1st_19", "2nd", "2nd_19", "ci_2nd", "ci_2nd_19", "no_auth"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/age_auth/$path.json")
        val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
        val response = KakaoGsonFactory.base.fromJson(body, AgeAuthResponse::class.java)

        assertEquals(expected["id"].asLong, response.id)
        if (expected.has("auth_level")) {
            assertEquals(expected["auth_level"].asString, response.authLevel!!.value)
        } else {
            assertNull(response.authLevel)
        }

        if (expected.has("auth_level_code")) {
            assertEquals(expected["auth_level_code"].asInt, response.authLevelCode)
        } else {
            assertNull(response.authLevelCode)
        }
        if (expected.has("bypass_age_limit")) {
            assertEquals(expected["bypass_age_limit"].asBoolean, response.bypassAgeLimit)
        } else {
            assertNull(response.bypassAgeLimit)
        }

        if (expected.has("authenticated_at")) {
            assertEquals(expected["authenticated_at"].asString, response.authenticatedAt)
        } else {
            assertNull(response.authenticatedAt)
        }
        if (expected.has("ci")) {
            assertEquals(expected["ci"].asString, response.ci)
        } else {
            assertNull(response.ci)
        }
    }
}