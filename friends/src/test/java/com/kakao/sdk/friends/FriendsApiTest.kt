package com.kakao.sdk.friends

import com.google.gson.JsonObject
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.network.ApiFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import java.io.File

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
class FriendsApiTest {
    private lateinit var api: FriendsApi
    private lateinit var retrofit: Retrofit
    private lateinit var server: MockWebServer

    @BeforeEach
    fun setup() {
        server = MockWebServer()
        server.start()
        retrofit = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
        api = retrofit.create(FriendsApi::class.java)
    }

    @Nested
    @DisplayName("/v1/friends")
    inner class GetFriends {
        @Test
        fun success() {
            val uri = javaClass.classLoader.getResource("json/friends/friends1.json")
            val file = File(uri.path)
            val body = String(file.readBytes())

            val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)

            runBlocking {
                val friendsResponse = api.friends().await()
                assertEquals(expected["total_count"].asInt, friendsResponse.totalCount)
                assertEquals(expected["elements"].asJsonArray.size(), friendsResponse.friends.size)
                assertEquals(expected["result_id"].asString, friendsResponse.resultId)
                assertNull(friendsResponse.beforeUrl)
                assertEquals(expected["after_url"].asString, friendsResponse.afterUrl)
            }
        }
    }
}