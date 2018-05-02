package com.kakao.sdk.kakaolink.entity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.kakaolink.Constants
import com.kakao.sdk.network.Utility
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Assertions.*
import java.util.stream.Stream

/**
 * @author kevin.kang. Created on 2018. 4. 30..
 */
class KakaoLinkResponseTest {
    @MethodSource("responseProvider")
    @ParameterizedTest fun parse(jsonString: String, jsonObject: JsonObject) {
        val response = Gson().fromJson(jsonString, KakaoLinkResponse::class.java)
        assertEquals(jsonObject[Constants.TEMPLATE_ID].asString, response.templateId)
    }

    companion object {
        val paths = listOf("default_commerce", "default_feed", "default_list", "default_location", "default_text",
            "validate")
        @Suppress("unused")
        @JvmStatic fun responseProvider(): Stream<Arguments> {
            return paths
                    .map { "json/$it.json" }
                    .map { Utility.getJson(it) }
                    .map { it to Gson().fromJson(it, JsonObject::class.java) }
                    .map { Arguments.of(it.first, it.second) }
                    .stream()
        }
    }

}