package com.kakao.sdk.auth.entity

import com.kakao.sdk.auth.Constants
import com.kakao.sdk.auth.AuthApi
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.ApiFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
class AuthApiRequestTest {
    private lateinit var api: AuthApi
    private lateinit var server: MockWebServer

    @BeforeEach fun setup() {
        server = MockWebServer()
        server.start()
        api = ApiFactory.withClient(
                server.url("/").toString(),
                OkHttpClient.Builder()).create(AuthApi::class.java)
        val response = MockResponse().setResponseCode(200)
        server.enqueue(response)
    }

    @CsvFileSource(resources = ["/csv/token_requests.csv"], numLinesToSkip = 1)
    @ParameterizedTest fun simple(
        clientId: String,
        redirectUri: String,
        approvalType: String,
        androidKeyHash: String,
        code: String?,
        refreshToken: String?,
        clientSecret: String?,
        grantType: String
    ) {

        runBlocking {
            api.issueAccessToken(clientId, redirectUri, approvalType, androidKeyHash,
                    authCode = code, refreshToken = refreshToken, clientSecret = clientSecret,
                    grantType = grantType)
//                .subscribe(TestObserver<AccessTokenResponse>())
        }

        val request = server.takeRequest()
        val params = Utility.parseQuery(request.body.readUtf8())

        assertEquals("POST", request.method)
        assertEquals(clientId, params[Constants.CLIENT_ID])
        assertEquals(redirectUri, params[Constants.REDIRECT_URI])
        assertEquals(approvalType, params[Constants.APPROVAL_TYPE])
        assertEquals(androidKeyHash, params[Constants.ANDROID_KEY_HASH])
        assertEquals(code, params[Constants.CODE])
        assertEquals(clientSecret, params[Constants.CLIENT_SECRET])
        assertEquals(grantType, params[Constants.GRANT_TYPE])
    }

    @AfterEach fun cleanup() {
        server.shutdown()
    }
}