package com.kakao.sdk.kakaotalk.domain

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.kakaotalk.Constants
import com.kakao.sdk.kakaotalk.entity.ChatListResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile
import com.kakao.sdk.network.Utility
import com.kakao.sdk.network.data.ApiService
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.URLDecoder
import java.util.stream.Stream

/**
 * @author kevin.kang. Created on 2018. 4. 30..
 */
class KakaoTalkApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: KakaoTalkApi

    @BeforeEach fun setup() {
        server = MockWebServer()
        server.start()
        api = ApiService.createApi(server.url("/"), KakaoTalkApi::class.java)
        val response = MockResponse().setResponseCode(200)
        server.enqueue(response)
    }

    @CsvFileSource(resources = ["/csv/profile.csv"], numLinesToSkip = 1)
    @ParameterizedTest fun profile(secureResource: Boolean?) {
        api.profile(secureResource = secureResource)
                .subscribe(TestObserver<TalkProfile>())
        val request = server.takeRequest()
        val params = Utility.parseQueryParams(request.requestUrl.query())

        if (secureResource == null) {
            assertFalse(params.containsKey(Constants.SECURE_RESOURCE))
            return
        }
        assertEquals(secureResource.toString(), params[Constants.SECURE_RESOURCE])
    }

    @CsvFileSource(resources = ["/csv/chat_list.csv"], numLinesToSkip = 1)
    @ParameterizedTest fun chatList(fromId: Int? = null,
                                    limit: Int? = null,
                                    order: String? = null,
                                    filter: String? = null) {
        api.chatList(fromId, limit, order, filter)
                .subscribe(TestObserver<ChatListResponse>())
        val request = server.takeRequest()
        val params = Utility.parseQueryParams(request.requestUrl.query())

        assertEquals(fromId, params[Constants.FROM_ID]?.toInt())
        assertEquals(limit, params[Constants.LIMIT]?.toInt())
        assertEquals(order, params[Constants.ORDER])
        assertEquals(filter, params[Constants.FILTER])
    }

    @MethodSource("sendMemoProvider")
    @ParameterizedTest fun sendMemo(templateId: String, templateArgs: Map<String, String>?) {
        api.sendMemo(templateId, templateArgs).subscribe(TestObserver<Void>())
        val request = server.takeRequest()
        val params = Utility.parseQueryParams(request.body.readUtf8())

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

    @MethodSource("sendMessageProvider")
    @ParameterizedTest fun sendMessage(receiverIdType: String,
                                       receiverId: String,
                                       templateId: String,
                                       templateArgs: Map<String, String>?) {
        api.sendMessage(receiverIdType, receiverId, templateId, templateArgs)
                .subscribe(TestObserver<Void>())
        val request = server.takeRequest()
        val params = Utility.parseQueryParams(request.body.readUtf8())

        assertEquals(receiverIdType, params[Constants.RECEIVER_ID_TYPE])
        assertEquals(receiverId, params[Constants.RECEIVER_ID])
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
        @JvmStatic fun sendMemoProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("1234", mapOf(Pair("key1", "value1"))),
                    Arguments.of("1234", null),
                    Arguments.of("1234", mapOf<String, String>()))
        }

        @Suppress("unused")
        @JvmStatic fun sendMessageProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of("user_id", "1234", "1234", null),
                    Arguments.of("uuid","1234", "1234", mapOf<String, String>()),
                    Arguments.of("chat_id","1234", "1234", mapOf(Pair("key1", "value1")))
            )
        }
    }
}