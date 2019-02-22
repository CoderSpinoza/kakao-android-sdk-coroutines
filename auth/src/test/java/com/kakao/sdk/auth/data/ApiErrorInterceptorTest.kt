package com.kakao.sdk.auth.data

import com.google.gson.JsonObject
import com.kakao.sdk.auth.*
import com.kakao.sdk.auth.exception.AgeVerificationException
import com.kakao.sdk.auth.exception.InvalidScopeException
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.network.ApiErrorCode
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.data.ApiException
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.HttpURLConnection
import java.util.stream.Stream
import org.mockito.Mockito.*
import retrofit2.HttpException
import retrofit2.Response
import java.lang.NullPointerException

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class ApiErrorInterceptorTest {

    private lateinit var interceptor: ApiErrorInterceptor
    private lateinit var authApiClient: AuthApiClient
    private lateinit var accessTokenRepo: AccessTokenRepo

    @BeforeEach fun setup() {
        val testToken = AccessToken(accessToken = "test_access_token", refreshToken = "test_refresh_token")
        accessTokenRepo = spy(TestAccessTokenRepo(testToken))
        authApiClient = spy(TestAuthApiClient())
        interceptor = ApiErrorInterceptor(authApiClient, accessTokenRepo,
                TestApplicationInfo("client_id", "individual", "client_secret"),
                TestContextInfo(kaHeader = "kaHeader", signingKeyHash = "key_hash", extras = JsonObject()))
    }

    @MethodSource("httpErrorProvider")
    @ParameterizedTest fun httpErrors(httpStatus: Int, body: String, errorCode: Int, exceptionType: Class<in ApiException>) {
        val retrofitResponse = Response.error<Void>(httpStatus,
                ResponseBody.create(MediaType.parse("application/json"), body))
        val exception = HttpException(retrofitResponse)

        val t = assertThrows<ApiException>("Error Message") {
            runBlocking {
                interceptor.handleApiError {
                    throw exception
                }
            }
        }
        assertEquals(exceptionType, t.javaClass)
    }

    @Test fun clientError() {
        val t = assertThrows<NullPointerException> {
            runBlocking {
                interceptor.handleApiError {
                    throw NullPointerException()
                }
            }
        }
    }

    @Test fun refreshTokenSucceeds() {
        val expectedValue = "success"
        val retrofitResponse = Response.error<Void>(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                ResponseBody.create(MediaType.parse("application/json"), Utility.getJson("json/api_errors/invalid_token.json")))
        val exception = HttpException(retrofitResponse)

        var first = true

        val result = runBlocking {
            interceptor.handleApiError {
                if (first) {
                    first = false
                    throw exception
                }
                return@handleApiError expectedValue
            }
        }
        assertEquals(expectedValue, result)
    }

    @Test fun refreshTokenFails() {

    }

    companion object {
        @Suppress("unused")
        @JvmStatic fun httpErrorProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            HttpURLConnection.HTTP_FORBIDDEN,
                            Utility.getJson("json/api_errors/invalid_scope.json"),
                            ApiErrorCode.INVALID_SCOPE_CODE,
                            InvalidScopeException::class.java
                    ),
                    Arguments.of(
                            HttpURLConnection.HTTP_INTERNAL_ERROR,
                            Utility.getJson("json/api_errors/internal_error.json"),
                            ApiErrorCode.INTERNAL_ERROR_CODE,
                            ApiException::class.java
                    ),
                    Arguments.of(
                            HttpURLConnection.HTTP_UNAUTHORIZED,
                            Utility.getJson("json/api_errors/need_age_auth.json"),
                            ApiErrorCode.AGE_VERIFICATION_NEEDED,
                            AgeVerificationException::class.java
                    )
            )
        }
    }
}