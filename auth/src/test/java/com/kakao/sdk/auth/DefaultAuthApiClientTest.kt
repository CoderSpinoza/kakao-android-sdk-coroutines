package com.kakao.sdk.auth

import com.google.gson.JsonParser
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.ApiFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.spy
import java.lang.RuntimeException
import java.net.HttpURLConnection

class DefaultAuthApiClientTest {
    private lateinit var authApiClient: DefaultAuthApiClient
    private lateinit var accessTokenRepo: AccessTokenRepo
    private lateinit var server: MockWebServer
    private lateinit var authApi: AuthApi

    @BeforeEach fun setup() {
        accessTokenRepo = spy(TestAccessTokenRepo(AccessToken()))
        server = MockWebServer()
        authApi = ApiFactory.withClient(
                server.url("/").toString(),
                OkHttpClient.Builder()).create(AuthApi::class.java)
        authApiClient = DefaultAuthApiClient(authApi, accessTokenRepo)
    }

    @AfterEach fun cleanup() {
        server.shutdown()
    }

    @Nested
    @DisplayName("Issuing access token")
    inner class IssueAccessToken {
        @Test fun with200Response() {
            val json = Utility.getJson("json/token/has_rt.json")
            val jsonElement = JsonParser().parse(json).asJsonObject
            server.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(json))

            runBlocking {
                authApi.issueAccessToken(
                        authCode = "auth_code", clientId = "client_id",
                        redirectUri = "redirect_uri", approvalType = Constants.INDIVIDUAL,
                        androidKeyHash = "android_key_hash", clientSecret = "client_secret")

                val request = server.takeRequest()
                val requestBody = Utility.parseQuery(request.body.readUtf8())
                assertEquals(Constants.AUTHORIZATION_CODE, requestBody[Constants.GRANT_TYPE])
            }
        }

        @Test fun with401Response() = runBlocking {
            val json = Utility.getJson("json/auth_errors/expired_refresh_token.json")
            val jsonElement = JsonParser().parse(json).asJsonObject
            server.enqueue(MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED).setBody(json))

            try {
                val result =
                        authApiClient.issueAccessToken(
                                authCode = "auth_code", clientId = "client_id",
                                redirectUri = "redirect_uri", approvalType = Constants.INDIVIDUAL,
                                androidKeyHash = "android_key_hash", clientSecret = "client_secret")
            } catch (e: RuntimeException) {
            }
        }

        @Test fun additional() {

//            runBlocking {
//                val deffered = authApiClient.issueAccessToken(authCode = "auth_code", clientId = "client_id", redirectUri = "redirect_uri",
//                        approvalType = Constants.INDIVIDUAL, androidKeyHash = "android_key_hash", clientSecret = "client_secret").await()
//            }
        }
    }

    @Nested
    @DisplayName("Refreshing access token")
    inner class RefreshAccessToken {
        @Test fun with200Response() {
//            val json = Single.just("json/token/no_rt.json")
//                    .map(Utility::getJson)
//            val jsonObject = json.map { JsonParser().parse(it) }.map { it.asJsonObject }.blockingGet()
//            json.map { MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(it) }
//                    .doOnSuccess { response -> server.enqueue(response) }
//                    .flatMap { authApiClient.refreshAccessToken(refreshToken = "refresh_token", clientId = "client_id",
//                            approvalType = Constants.INDIVIDUAL, androidKeyHash = "android_key_hash", clientSecret = "client_secret")
//                    }.subscribe(observer)
//            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
//            observer.assertNoErrors()
//            observer.assertValueCount(1)

//            observer.assertValue {  }

//            verify(accessTokenRepo).toCache(any())
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}