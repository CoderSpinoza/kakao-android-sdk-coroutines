package com.kakao.sdk.kakaostory.entity

import com.google.gson.JsonObject
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaostory.Constants
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class LinkInfoTest {
    @ValueSource(strings = ["linkinfo1", "linkinfo2"])
    @ParameterizedTest fun parse(path: String) {
        val body = Utility.getJson("json/linkinfo/$path.json")
        val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
        val response = KakaoGsonFactory.base.fromJson(body, LinkInfo::class.java)

        assertEquals(expected[Constants.URL].asString, response.url)
        assertEquals(expected[Constants.REQUESTED_URL].asString, response.requestedUrl)
        assertEquals(expected[Constants.HOST].asString, response.host)
        assertEquals(expected[Constants.TITLE].asString, response.title)
        assertEquals(expected[Constants.DESCRIPTION].asString, response.description)
        assertEquals(expected[Constants.SECTION].asString, response.section)
        assertEquals(expected[Constants.TYPE].asString, response.type)

        assertEquals(expected[Constants.IMAGE].asJsonArray.size(), response.images?.size)

        val array = expected[Constants.IMAGE].asJsonArray
        array.forEachIndexed { i, json ->
            val expectedImage = json.asString
            val image = response.images!![i]
            assertEquals(expectedImage, image)
        }
    }
}