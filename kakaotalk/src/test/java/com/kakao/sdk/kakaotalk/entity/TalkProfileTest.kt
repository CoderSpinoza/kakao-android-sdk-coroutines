package com.kakao.sdk.kakaotalk.entity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.kakaotalk.Constants
import com.kakao.sdk.common.Utility
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 2018. 4. 28..
 */
class TalkProfileTest {
    @ValueSource(strings = ["full_profile"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/profile/$path.json")
        val expected = Gson().fromJson(body, JsonObject::class.java)
        val response = Gson().fromJson(body, TalkProfile::class.java)

        assertEquals(expected[Constants.NICKNAME].asString, response.nickname)
        assertEquals(expected[Constants.PROFILE_IMAGE_URL].asString, response.profileImageUrl)
        assertEquals(expected[Constants.THUMBNAIL_URL].asString, response.thumbnailUrl)
        assertEquals(expected[Constants.COUNTRY_ISO].asString, response.countryISO)
    }
}