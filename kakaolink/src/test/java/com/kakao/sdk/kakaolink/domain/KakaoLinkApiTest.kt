package com.kakao.sdk.kakaolink.domain

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.kakaolink.Constants
import com.kakao.sdk.kakaolink.entity.KakaoLinkResponse
import com.kakao.sdk.network.Utility
import com.kakao.sdk.network.data.ApiService
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Assertions.*
import java.net.URLDecoder
import java.util.stream.Stream

/**
 * @author kevin.kang. Created on 2018. 4. 30..
 */
class KakaoLinkApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: KakaoLinkApi
    @BeforeEach fun setup() {
        server = MockWebServer()
        server.start()
        api = ApiService.createApi(server.url("/"), KakaoLinkApi::class.java)

        val response = MockResponse().setResponseCode(200)
        server.enqueue(response)
    }

    @MethodSource("validateProvider")
    @ParameterizedTest fun validate(templateId: String, templateArgs: Map<String, String>?) {
        api.validateCustom(templateId, templateArgs).subscribe(TestObserver<KakaoLinkResponse>())
        val request = server.takeRequest()
        val params = Utility.parseQueryParams(request.requestUrl.query())

        assertEquals("4.0", params[Constants.LINK_VER])
        assertEquals(templateId, params[Constants.TEMPLATE_ID])
        if (templateArgs == null) {
            assertFalse(params.containsKey(Constants.TEMPLATE_ARGS))
            return
        }

        val decoded = URLDecoder.decode(params[Constants.TEMPLATE_ARGS], "UTF-8")
        val decodedJson = Gson().fromJson(decoded, JsonObject::class.java)
        assertEquals(templateArgs.size, decodedJson.size())

        for ((k, v) in templateArgs) {
            assertEquals(v, decodedJson[k].asString)
        }
    }

    @MethodSource("scrapProvider")
    @ParameterizedTest fun scrap(url: String, templateId: String, templateArgs: Map<String, String>?) {
        api.validateScrap(url, templateId, templateArgs).subscribe(TestObserver<KakaoLinkResponse>())
        val request = server.takeRequest()
        val params = Utility.parseQueryParams(request.requestUrl.query())

        assertEquals("4.0", params[Constants.LINK_VER])
        assertEquals(url, params[Constants.REQUEST_URL])
        assertEquals(templateId, params[Constants.TEMPLATE_ID])

        if (templateArgs == null) {
            assertFalse(params.containsKey(Constants.TEMPLATE_ARGS))
            return
        }

        val decoded = URLDecoder.decode(params[Constants.TEMPLATE_ARGS], "UTF-8")
        val decodedJson = Gson().fromJson(decoded, JsonObject::class.java)
        assertEquals(templateArgs.size, decodedJson.size())

        for ((k, v) in templateArgs) {
            assertEquals(v, decodedJson[k].asString)
        }
    }

    @AfterEach fun cleanup() {
        server.shutdown()
    }

    companion object {
        @Suppress("unused")
        @JvmStatic fun validateProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1234", null),
                    Arguments.of("1234", mapOf<String, String>()),
                    Arguments.of("1234", mapOf(Pair("key1", "value1"))),
                    Arguments.of("1234", mapOf(Pair("key1", "value1"), Pair("key2", "\"value2\""))))
        }

        @Suppress("unused")
        @JvmStatic fun scrapProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("request_url", "1234", null),
                    Arguments.of("request_url", "1234", mapOf(Pair("key1", "value1"))),
                    Arguments.of("request_url", "1234", mapOf(Pair("key1", "value1"), Pair("key2", "\"value2\"")))
            )
        }
    }
}