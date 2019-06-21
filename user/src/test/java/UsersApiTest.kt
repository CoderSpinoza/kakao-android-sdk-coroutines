package com.kakao.sdk.user

import com.google.gson.JsonObject
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.ApiFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import java.io.File
import java.net.URLDecoder

/**
 * @author kevin.kang. Created on 2018. 4. 24..
 */
class UsersApiTest {
    private lateinit var api: UserApi
    private lateinit var retrofit: Retrofit
    private lateinit var server: MockWebServer

    private lateinit var body: String
    private lateinit var expected: JsonObject

    @BeforeEach
    fun setup() {
        server = MockWebServer()
        server.start()
        retrofit = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
        api = retrofit.create(UserApi::class.java)
    }

    @Test
    fun me() = runBlocking {
        val body = Utility.getJson("json/users/deprecated.json")
//        val expected = KakaoGsonFactory.base.fromJson(body, JsonObject::class.java)
        val response = MockResponse().setResponseCode(200).setBody(body)
        server.enqueue(response)
        api.me()
        val request = server.takeRequest()
        assertEquals("GET", request.method)
//        observer.assertValue { user ->
//            return@assertValue expected["kaccount_email"].asString == user.email
//                    && expected["kaccount_email_verified"].asBoolean == user.emailVerified
//                    && 1376016924426814086 == user.id
//                    && expected["uuid"].asString == user.uuid
//                    && expected["service_user_id"].asLong == user.serviceUserId
//                    && expected["remaining_invite_count"].asInt == user.remainingInviteCount
//                    && expected["remaining_group_msg_count"].asInt == user.remainingGroupMsgCount
//                    && user.properties!!.containsKey("nickname")
//                    && user.properties!!.containsKey("thumbnail_image")
//                    && user.properties!!.containsKey("profile_image")
//        }
    }

    @Nested
    @DisplayName(Constants.V1_ACCESS_TOKEN_INFO_PATH)
    inner class TokenInfo {
        @BeforeEach
        fun setup() {
            body = Utility.getJson("json/token_info/internal.json")
            expected = KakaoGsonFactory.base.fromJson<JsonObject>(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
        }

        @Test
        fun accessTokenInfo() = runBlocking {
            val response = api.accessTokenInfo()
            assertEquals(expected["id"].asLong, response.id)
            assertEquals(expected["expiresInMillis"].asLong, response.expiresInMillis)
            assertEquals(expected["kaccount_id"].asLong, response.kaccountId)
            assertEquals(expected["appId"].asLong, response.appId)
        }
    }

    @Nested
    @DisplayName(Constants.V1_UPDATE_PROFILE_PATH)
    inner class UpdateProfile {
        @BeforeEach
        fun setup() {
            body = Utility.getJson("json/user_id.json")
            expected = KakaoGsonFactory.base.fromJson<JsonObject>(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
        }

        @Test
        fun updateProfile() = runBlocking {
            val properties = mapOf(Pair("key1", "value1"), Pair("key2", "value2"))
            val response = api.updateProfile(properties = properties)
            val request = server.takeRequest()
            val requestBody = Utility.parseQuery(request.body.readUtf8())

            assertEquals("POST", request.method)
            val requestProperties =
                    KakaoGsonFactory.base.fromJson<JsonObject>(
                            URLDecoder.decode(requestBody["properties"], "UTF-8"),
                            JsonObject::class.java)
            assertEquals("value1", requestProperties["key1"].asString)
            assertEquals("value2", requestProperties["key2"].asString)
        }
    }

    @Nested
    @DisplayName("/v1/user/logout and /v1/user/unlink")
    inner class LogoutAndUnlink {
        @BeforeEach
        fun setup() {
            body = Utility.getJson("json/user_id.json")
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
        }

        @Test
        fun logout() = runBlocking {
            api.logout()
        }

        @Test
        fun unlink() = runBlocking {
            api.unlink()
        }
    }

    @AfterEach
    fun cleanup() {
        server.shutdown()
    }
}