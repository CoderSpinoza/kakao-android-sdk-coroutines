package com.kakao.sdk.kakaolink

import com.google.gson.JsonObject
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaolink.entity.KakaoLinkResponse
import com.kakao.sdk.message.template.FeedTemplate
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.LinkObject
import com.kakao.sdk.network.ApiFactory
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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
        api = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
                .create(KakaoLinkApi::class.java)

        val response = MockResponse().setResponseCode(200)
        server.enqueue(response)
    }

    @MethodSource("validateProvider")
    @ParameterizedTest fun validate(templateId: String, templateArgs: Map<String, String>?) {
        runBlocking {
            val response = api.validateCustom(templateId, templateArgs)
        }
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals("4.0", params[Constants.LINK_VER])
        assertEquals(templateId, params[Constants.TEMPLATE_ID])
        if (templateArgs == null) {
            assertFalse(params.containsKey(Constants.TEMPLATE_ARGS))
            return
        }

        val decoded = URLDecoder.decode(params[Constants.TEMPLATE_ARGS], "UTF-8")
        val decodedJson = KakaoGsonFactory.base.fromJson(decoded, JsonObject::class.java)
        assertEquals(templateArgs.size, decodedJson.size())

        for ((k, v) in templateArgs) {
            assertEquals(v, decodedJson[k].asString)
        }
    }

    @Test fun default() {
        val template = FeedTemplate(
                ContentObject("title", "imageUrl", LinkObject("webUrl"))
        )

        runBlocking {
            val response = api.validateDefault(template)

        }
        val request = server.takeRequest()
        val params = com.kakao.sdk.common.Utility.parseQuery(request.requestUrl.query())

        assertEquals("4.0", params[Constants.LINK_VER])
        assertNotNull(params[Constants.TEMPLATE_OBJECT])
        System.out.println(params)
    }

    @MethodSource("scrapProvider")
    @ParameterizedTest fun scrap(url: String, templateId: String, templateArgs: Map<String, String>?) {
        api.validateScrap(url, templateId, templateArgs)
        val request = server.takeRequest()
        val params = com.kakao.sdk.common.Utility.parseQuery(request.requestUrl.query())

        assertEquals("4.0", params[Constants.LINK_VER])
        assertEquals(url, params[Constants.REQUEST_URL])
        assertEquals(templateId, params[Constants.TEMPLATE_ID])

        if (templateArgs == null) {
            assertFalse(params.containsKey(Constants.TEMPLATE_ARGS))
            return
        }

        val decoded = URLDecoder.decode(params[Constants.TEMPLATE_ARGS], "UTF-8")
        val decodedJson = KakaoGsonFactory.base.fromJson(decoded, JsonObject::class.java)
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