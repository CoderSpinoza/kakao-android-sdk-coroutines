package com.kakao.sdk.user

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.network.Utility
import com.kakao.sdk.user.entity.User
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class UserTest {
    @ValueSource(strings = ["only_email", "only_phone", "preregi"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/users/$path.json")
        val expected = Gson().fromJson(body, JsonObject::class.java)
        val response = Gson().fromJson(body, User::class.java)

        assertEquals(expected[Constants.ID].asLong, response.id)
        if (expected.has(Constants.HAS_SIGNED_UP)) {
            assertEquals(expected[Constants.HAS_SIGNED_UP].asBoolean, response.hasSignedUp)
        } else {
            assertTrue(response.hasSignedUp)
        }

        if (expected.has(Constants.PROPERTIES)) {
            assertEquals(expected[Constants.PROPERTIES].asJsonObject.keySet().size, response.properties.size)
        }

        if (expected.has(Constants.FOR_PARTNER)) {
            assertEquals(expected[Constants.FOR_PARTNER].asJsonObject.keySet().size, response.forPartners.size)
        }

        if (expected.has(Constants.KAKAO_ACCOUNT)) {
            assertNotNull(response.kakaoAccount)

            val expectedAccount = expected[Constants.KAKAO_ACCOUNT].asJsonObject
            val account = response.kakaoAccount

            if (expectedAccount.has(Constants.HAS_EMAIL)) {
                assertEquals(expectedAccount[Constants.HAS_EMAIL].asBoolean, account.hasEmail)
            } else {
                assertNull(account.hasEmail)
            }
            if (expectedAccount.has(Constants.IS_EMAIL_VERIFIED)) {
                assertEquals(expectedAccount[Constants.IS_EMAIL_VERIFIED].asBoolean, account.isEmailVerified)
            } else {
                assertNull(account.isEmailVerified)
            }
            if (expectedAccount.has(Constants.EMAIL)) {
                assertEquals(expectedAccount[Constants.EMAIL].asString, account.email)
            } else {
                assertNull(account.email)
            }
            if (expectedAccount.has(Constants.HAS_PHONE_NUMBER)) {
                assertEquals(expectedAccount[Constants.HAS_PHONE_NUMBER].asBoolean, account.hasPhoneNumber)
            } else {
                assertNull(account.hasPhoneNumber)
            }
            if (expectedAccount.has(Constants.PHONE_NUMBER)) {
                assertEquals(expectedAccount[Constants.PHONE_NUMBER].asString, account.phoneNumber)
            } else {
                assertNull(account.phoneNumber)
            }
            if (expectedAccount.has(Constants.DISPLAY_ID)) {
                assertEquals(expectedAccount[Constants.DISPLAY_ID].asString, account.displayId)
            } else {
                assertNull(account.displayId)
            }
        }
    }
}