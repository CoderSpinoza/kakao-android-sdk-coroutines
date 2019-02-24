package com.kakao.sdk.auth.network

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.exception.AgeVerificationException
import com.kakao.sdk.auth.exception.InvalidScopeException
import com.kakao.sdk.auth.exception.InvalidTokenException
import com.kakao.sdk.auth.model.MissingScopesErrorResponse
import com.kakao.sdk.common.KakaoSdkProvider
import com.kakao.sdk.common.ApplicationInfo
import com.kakao.sdk.common.ContextInfo
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.network.ApiErrorCode
import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException
import kotlinx.coroutines.Deferred
import retrofit2.HttpException

suspend fun <T> Deferred<T>.handleApiError(interceptor: ApiErrorInterceptor): T {
    return interceptor.handleApiError { this.await() }
}

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class ApiErrorInterceptor(
    private val authApiClient: AuthApiClient = AuthApiClient.instance,
    private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance,
    private val appInfo: ApplicationInfo = KakaoSdkProvider.applicationContextInfo,
    private val contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo
) {

    suspend fun <T> handleApiError(block: suspend () -> T): T {
        return errorOnRefresh { refreshAccessToken { translateError {
            block()
        } } }
    }

    suspend fun <T> errorOnRefresh(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: InvalidTokenException) {
            accessTokenRepo.clearCache()
            throw e
        }
    }
    suspend fun <T> refreshAccessToken(block: suspend () -> T): T {
        return try {
            block()
        } catch (t: InvalidTokenException) {
            val token = accessTokenRepo.observe().openSubscription().receive()
            if (token.refreshToken == null) throw t
            val result = authApiClient.refreshAccessToken(
                    token.refreshToken,
                    appInfo.clientId,
                    appInfo.approvalType,
                    contextInfo.signingKeyHash,
                    appInfo.clientSecret)
            block()
        }
    }

    suspend fun <T> translateError(block: suspend () -> T): T {
        try {
            return block()
        } catch (t: HttpException) {
            val errorString = t.response().errorBody()?.string()
            val response = KakaoGsonFactory.base.fromJson(errorString, ApiErrorResponse::class.java)
            when (response.code) {
                ApiErrorCode.INVALID_TOKEN_CODE -> throw InvalidTokenException(t.code(), response)
                ApiErrorCode.INVALID_SCOPE_CODE -> {
                    val scopeErrorResponse =
                            KakaoGsonFactory.base.fromJson(errorString,
                                    MissingScopesErrorResponse::class.java)
                    throw InvalidScopeException(t.code(), scopeErrorResponse)
                }
                ApiErrorCode.AGE_VERIFICATION_NEEDED ->
                    throw AgeVerificationException(t.code(), response)
            }
            throw ApiException(t.code(), response.code, response.message)
        }
    }

    companion object {
        val instance by lazy {
            ApiErrorInterceptor()
        }
    }
}