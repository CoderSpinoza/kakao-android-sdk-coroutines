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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 5. 2..
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class ApiErrorInterceptor constructor(
        private val authApiClient: AuthApiClient = AuthApiClient.instance,
        private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance,
        private val appInfo: ApplicationInfo = KakaoSdkProvider.applicationContextInfo,
        private val contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo
) {

    suspend fun <T> handleApiError(block: suspend () -> T): T {
        return errorOnRefresh {
            refreshAccessToken {
                translateError {
                    block()
                }
            }
        }
    }

    private suspend fun <T> errorOnRefresh(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: InvalidTokenException) {
            accessTokenRepo.clearCache()
            throw e
        }
    }

    private suspend fun <T> refreshAccessToken(block: suspend () -> T): T {
        return try {
            block()
        } catch (t: InvalidTokenException) {
            val token = accessTokenRepo.observe().value
            if (token.refreshToken == null) throw t
            authApiClient.refreshAccessToken(
                    token.refreshToken,
                    appInfo.clientId,
                    appInfo.approvalType,
                    contextInfo.signingKeyHash,
                    appInfo.clientSecret)
            block()
        }
    }

    internal suspend fun <T> translateError(block: suspend () -> T): T {
        return try {
            block()
        } catch (t: HttpException) {
            val errorString = t.response()?.errorBody()?.string()
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