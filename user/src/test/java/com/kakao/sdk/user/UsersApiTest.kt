package com.kakao.sdk.user

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.data.ApiFactory
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import retrofit2.Retrofit
import java.io.File
import java.net.URLDecoder
import java.util.concurrent.TimeUnit


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

    @Test fun me() {
        val body = Utility.getJson("json/users/deprecated.json")

//        val expected = Gson().fromJson(body, JsonObject::class.java)

        val response = MockResponse().setResponseCode(200).setBody(body)
        server.enqueue(response)

        val observer = TestObserver<User>()
        api.me(true).subscribe(observer)

        observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
        observer.assertNoErrors()
        observer.assertValueCount(1)

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
        @BeforeEach fun setup() {
            val classloader = javaClass.classLoader ?: throw NullPointerException()
            val uri = classloader.getResource("json/token_info/internal.json")
            val file = File(uri.path)
            body = String(file.readBytes())
            expected = Gson().fromJson<JsonObject>(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
        }

        @Test fun accessTokenInfo() {
            val observer = TestObserver<AccessTokenInfo>()
            api.accessTokenInfo().subscribe(observer)
            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)
            observer.assertValue {
                expected["id"].asLong == it.id
                && expected["expiresInMillis"].asLong == it.expiresInMillis
                && expected["kaccount_id"].asLong == it.kaccountId
                && expected["appId"].asLong == it.appId
            }
        }
    }

    @Nested
    @DisplayName(Constants.V1_UPDATE_PROFILE_PATH)
    inner class UpdateProfile {
        @BeforeEach fun setup() {
            val classloader = javaClass.classLoader ?: throw NullPointerException()
            val uri = classloader.getResource("json/user_id.json")
            val file = File(uri.path)
            body = String(file.readBytes())
            expected = Gson().fromJson<JsonObject>(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
        }

        @Test fun updateProfile() {
            val observer = TestObserver<UserIdResponse>()
            val properties = mapOf(Pair("key1", "value1"), Pair("key2", "value2"))
            api.updateProfile(properties = properties).subscribe(observer)
            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)

            val request = server.takeRequest()
            val requestBody = Utility.parseQuery(request.body.readUtf8())

            assertEquals("POST", request.method)
            val requestProperties = Gson().fromJson<JsonObject>(URLDecoder.decode(requestBody["properties"], "UTF-8"), JsonObject::class.java)
            assertEquals("value1", requestProperties["key1"].asString)
            assertEquals("value2", requestProperties["key2"].asString)
            observer.assertValue {
                expected["id"].asLong == it.id
            }
        }
    }

    @Nested
    @DisplayName("/v1/user/logout and /v1/user/unlink")
    inner class LogoutAndUnlink {
        @BeforeEach fun setup() {
            val classloader = javaClass.classLoader ?: throw NullPointerException()
            val uri = classloader.getResource("json/user_id.json")
            val file = File(uri.path)
            body = String(file.readBytes())
            expected = Gson().fromJson(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
        }

        @Test fun logout() {
            val observer = TestObserver<UserIdResponse>()
            api.logout().subscribe(observer)
            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)
            observer.assertValue {
                expected["id"].asLong == it.id
            }

        }

        @Test fun unlink() {
            val observer = TestObserver<UserIdResponse>()
            api.unlink().subscribe(observer)
            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)
            observer.assertValue {
                expected["id"].asLong == it.id
            }
        }
    }

    @AfterEach fun cleanup() {
        server.shutdown()
    }
}