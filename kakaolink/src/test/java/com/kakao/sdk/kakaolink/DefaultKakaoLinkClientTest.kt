package com.kakao.sdk.kakaolink

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.kakao.sdk.common.Utility
import com.kakao.sdk.message.template.FeedTemplate
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.LinkObject
import com.kakao.sdk.network.ApiFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * @author kevin.kang. Created on 19/02/2019..
 */
@RunWith(RobolectricTestRunner::class)
class DefaultKakaoLinkClientTest {
    private lateinit var server: MockWebServer
    private lateinit var client: DefaultKakaoLinkClient

    @Before fun setup() {
        server = MockWebServer()
        client = DefaultKakaoLinkClient(
                contextInfo = TestContextInfo(kaHeader = "ka_header", signingKeyHash = "key_hash", extras = JsonObject()),
                api = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
                        .create(KakaoLinkApi::class.java)
        )
    }

    @After fun cleanup() {
        server.shutdown()
    }

    @Test fun defaultTemplate() {
        val json = Utility.getJson("json/default_feed.json")
        val jsonObject = JsonParser().parse(json).asJsonObject
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        runBlocking {
            val intent = client.defaultTemplateIntent(
                    FeedTemplate(
                            ContentObject("title", "imageUrl", LinkObject())
                    )
            )
            assertNotNull(intent?.data)
            val uri = intent.data
            assertEquals(jsonObject[Constants.TEMPLATE_ID].asString, uri!!.getQueryParameter(Constants.TEMPLATE_ID))
        }
    }
}