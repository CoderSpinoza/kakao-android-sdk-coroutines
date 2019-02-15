package com.kakao.sdk.auth.data

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TestAccessTokenRepo
import com.kakao.sdk.auth.TestAuthApiClient
import com.kakao.sdk.auth.exception.AgeVerificationException
import com.kakao.sdk.auth.exception.InvalidScopeException
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.network.ApiErrorCode
import com.kakao.sdk.network.ApplicationInfo
import com.kakao.sdk.network.Utility
import com.kakao.sdk.network.data.ApiException
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.HttpURLConnection
import java.util.stream.Stream
import org.mockito.Mockito.*
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.Callable

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
        interceptor = ApiErrorInterceptor(authApiClient, accessTokenRepo, ApplicationInfo("client_id", "individual", "key_hash", "client_secret"))
    }

    @MethodSource("httpErrorProvider")
    @ParameterizedTest fun httpErrors(httpStatus: Int, body: String, errorCode: Int, exceptionType: Class<in ApiException>) {
        val retrofitResponse =Response.error<Void>(httpStatus,
                ResponseBody.create(MediaType.parse("application/json"), body))
        val exception = HttpException(retrofitResponse)
        val observer = TestObserver<Void>()
        Single.error<Void>(exception).compose(interceptor.handleApiError())
                .subscribe(observer)
        observer.assertError {
            it.javaClass == exceptionType
        }
    }

    @Test fun clientError() {
        val observer = TestObserver<Void>()
        Single.error<Void>(NullPointerException()).compose(interceptor.handleApiError())
                .subscribe(observer)

        observer.assertError {
            it.javaClass == NullPointerException::class.java
        }
    }

    @Test fun refreshTokenSucceeds() {
        val expectedValue = "success"
        val retrofitResponse = Response.error<Void>(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                ResponseBody.create(MediaType.parse("application/json"), Utility.getJson("json/api_errors/invalid_token.json")))
        val exception = HttpException(retrofitResponse)
        val observer = TestObserver<Any>()

        val callable = object: Callable<String> {
            var subscribedBefore = false
            override fun call(): String {
                if (subscribedBefore) {
                    return expectedValue
                }
                subscribedBefore = true
                throw exception
            }
        }

        Single.fromCallable(callable).compose(interceptor.handleApiError())
                .subscribe(observer)
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValueCount(1)
        observer.assertValue(expectedValue)
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