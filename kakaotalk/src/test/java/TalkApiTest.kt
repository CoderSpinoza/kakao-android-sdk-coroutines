package com.kakao.sdk.kakaotalk.domain

import com.google.gson.JsonObject
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaotalk.Constants
import com.kakao.sdk.kakaotalk.TalkApi
import com.kakao.sdk.network.ApiFactory
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
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.MethodSource
import java.net.URLDecoder
import java.util.stream.Stream

/**
 * @author kevin.kang. Created on 2018. 4. 30..
 */
class TalkApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: TalkApi
    private lateinit var response: MockResponse

    @BeforeEach
    fun setup() {
        server = MockWebServer()
        server.start()
        api = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
                .create(TalkApi::class.java)
        response = MockResponse().setResponseCode(200)
    }

    @Test
    fun profile() = runBlocking {
        response.setBody(Utility.getJson("json/profile/full_profile.json"))
        server.enqueue(response)
        api.profile()
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())
        assertEquals("true", params[Constants.SECURE_RESOURCE])
    }

    @CsvFileSource(resources = ["/csv/chat_list.csv"], numLinesToSkip = 1)
    @ParameterizedTest
    fun chatList(
            fromId: Int? = null,
            limit: Int? = null,
            order: String? = null,
            filter: String? = null
    ) = runBlocking {
        response.setBody(Utility.getJson("json/chat_list/list.json"))
        server.enqueue(response)
        api.chatList(fromId, limit, order, filter)
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals(fromId, params[Constants.FROM_ID]?.toInt())
        assertEquals(limit, params[Constants.LIMIT]?.toInt())
        assertEquals(order, params[Constants.ORDER])
        assertEquals(filter, params[Constants.FILTER])
    }

    @MethodSource("sendMemoProvider")
    @ParameterizedTest
    fun sendMemo(templateId: String, templateArgs: Map<String, String>?) = runBlocking {
        server.enqueue(response)
        api.customMemo(templateId, templateArgs)
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.body.readUtf8())

        assertEquals(templateId, params[Constants.TEMPLATE_ID])
        if (templateArgs == null) {
            assertFalse(params.containsKey(Constants.TEMPLATE_ARGS))
            return@runBlocking
        }

        val decoded = URLDecoder.decode(params[Constants.TEMPLATE_ARGS], "UTF-8")
        val decodedJson = KakaoGsonFactory.base.fromJson(decoded, JsonObject::class.java)
        assertEquals(templateArgs.size, decodedJson.size())

        for ((k, v) in templateArgs) {
            assertEquals(v, decodedJson[k].asString)
        }
    }

    @AfterEach
    fun cleanup() {
        server.shutdown()
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun sendMemoProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1234", mapOf(Pair("key1", "value1"))),
                    Arguments.of("1234", null),
                    Arguments.of("1234", mapOf<String, String>()))
        }
    }
}