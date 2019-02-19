package com.kakao.sdk.auth

import com.google.gson.JsonParser
import com.kakao.sdk.auth.exception.AuthResponseException
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.data.ApiFactory
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class DefaultAuthApiClientTest {
    private lateinit var authApiClient: DefaultAuthApiClient
    private lateinit var accessTokenRepo: AccessTokenRepo
    private lateinit var server: MockWebServer
    private lateinit var observer: TestObserver<AccessTokenResponse>

    @BeforeEach fun setup() {
        accessTokenRepo = spy(TestAccessTokenRepo(AccessToken()))
        server = MockWebServer()
        val authApi = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder()).create(AuthApi::class.java)
        authApiClient = DefaultAuthApiClient(authApi, accessTokenRepo)
        observer = TestObserver()
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

            authApiClient.issueAccessToken(authCode = "auth_code", clientId = "client_id", redirectUri = "rediect_uri",
                    approvalType = Constants.INDIVIDUAL, androidKeyHash = "android_key_hash", clientSecret = "client_secret")
                    .subscribe(observer)

            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)

            val request = server.takeRequest()
            val requestBody = Utility.parseQuery(request.body.readUtf8())

            Assertions.assertEquals(Constants.AUTHORIZATION_CODE, requestBody[Constants.GRANT_TYPE])

            observer.assertValue {
                return@assertValue jsonElement[Constants.ACCESS_TOKEN].asString == it.accessToken &&
                        jsonElement[Constants.REFRESH_TOKEN].asString == it.refreshToken &&
                        jsonElement[Constants.EXPIRES_IN].asLong == it.accessTokenExpiresIn &&
                        jsonElement[Constants.REFRESH_TOKEN_EXPIRES_IN].asLong == it.refreshTokenExpiresIn
            }
            verify(accessTokenRepo).toCache(any())
        }

        @Test fun with401Response() {
            val json = Utility.getJson("json/auth_errors/expired_refresh_token.json")
            val jsonElement = JsonParser().parse(json).asJsonObject
            server.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED).setBody(json))

            authApiClient.issueAccessToken(authCode = "auth_code", clientId = "client_id", redirectUri = "rediect_uri",
                    approvalType = Constants.INDIVIDUAL, androidKeyHash = "android_key_hash", clientSecret = "client_secret")
                    .subscribe(observer)

            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertError {
                return@assertError it.javaClass == AuthResponseException::class.java &&
                        it is AuthResponseException &&
                        it.httpStatus == HttpURLConnection.HTTP_UNAUTHORIZED &&
                        it.response.error == jsonElement[Constants.ERROR].asString &&
                        it.response.errorDescription == jsonElement[Constants.ERROR_DESCRIPTION].asString
            }
            verify(accessTokenRepo, never()).toCache(any())
        }
    }

    @Nested
    @DisplayName("Refreshing access token")
    inner class RefreshAccessToken {
        @Test fun with200Response() {
            val json = Single.just("json/token/no_rt.json")
                    .map(Utility::getJson)
            val jsonObject = json.map { JsonParser().parse(it) }.map { it.asJsonObject }.blockingGet()
            json.map { MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(it) }
                    .doOnSuccess { response -> server.enqueue(response) }
                    .flatMap { authApiClient.refreshAccessToken(refreshToken = "refresh_token", clientId = "client_id",
                            approvalType = Constants.INDIVIDUAL, androidKeyHash = "android_key_hash", clientSecret = "client_secret")
                    }.subscribe(observer)
            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)

//            observer.assertValue {  }

            verify(accessTokenRepo).toCache(any())
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return  uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}